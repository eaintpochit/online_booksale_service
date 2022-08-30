package com.example.crudapi.serviceImpl;

import com.example.crudapi.domain.*;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.BankCardRepository;
import com.example.crudapi.repository.BookRepository;
import com.example.crudapi.repository.MemberRepository;
import com.example.crudapi.repository.PurchaseHistoryRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
public class BookPurchaseServiceImpl {

    @Autowired
    BankCardRepository bankCardRepo;
    @Autowired
    BookRepository bookRepo;
    @Autowired
    PurchaseHistoryRepository purchaseHisRepo;
    @Autowired
    ValidationforPurchaseBook validationforPurchaseBook;
    @Autowired
    BankCardServiceImpl bankCardServiceImpl;
    @Autowired
    MemberServiceImpl mberServiceImpl;
    @Autowired
    MemberRepository mberRepo;

    Book myBook = new Book();
    ResponseMessage responseMessage = new ResponseMessage();
    Gson gson = new Gson();

    public ResponseEntity<?> bookPurchase(String id, String cardNumber, int bookNumber, String mberId) {

        BankCard getBankCard = null;

        ResponseEntity<?> validateCardNumber = validationforPurchaseBook.validateCardNumber(cardNumber);
        ResponseEntity<?> mberResponse = validationforPurchaseBook.validateMber(mberId);

        if (mberResponse.getStatusCode() == HttpStatus.FOUND) {
            JsonObject jsonMber = gson.fromJson(Objects.requireNonNull(mberResponse.getBody()).toString(),JsonObject.class);

            if (validateCardNumber.getStatusCode() == HttpStatus.FOUND) {
                String strBankCard = Objects.requireNonNull(validateCardNumber.getBody()).toString();
                try {
                    JsonObject jsonBankCard = gson.fromJson(strBankCard, JsonObject.class);
                    getBankCard = gson.fromJson(jsonBankCard, BankCard.class);
                } catch (JsonParseException ignored) { }

                ResponseEntity<?> bookResponse = operationBook(id, bookNumber,getBankCard);
                JsonObject jsonBook;
                if(bookResponse.getStatusCode() == HttpStatus.BAD_REQUEST){
                    return bookResponse;
                }
               try {
                   jsonBook = gson.fromJson(Objects.requireNonNull(bookResponse.getBody()).toString(),JsonObject.class);
                   myBook = gson.fromJson(jsonBook, Book.class);
               }catch (JsonParseException | NullPointerException ignored){}


                myBook.setQuantity(bookNumber);
                String strBook = gson.toJson(myBook);


                JsonObject prepareHis = new JsonObject();
                prepareHis.addProperty("memberName",jsonMber.get("memberName").getAsString());
                prepareHis.addProperty("purchaseBook",strBook);
                prepareHis.addProperty("bankCardNumber",cardNumber);
                prepareHis.addProperty("purchaseDateTime", Instant.now().toString());

                return new ResponseEntity<>(prepareHis, HttpStatus.OK);
            }
            return validateCardNumber;
        }

        return mberResponse;

    }

    public ResponseEntity<?> operationBook(String bookId, int bookNumber,BankCard bankCard){

        ResponseEntity<?> validateBook = validationforPurchaseBook.validateRequestBook(bookId);
        if (validateBook.getStatusCode() == HttpStatus.FOUND) {
            String strBook = Objects.requireNonNull(validateBook.getBody()).toString();
            Book book = new Book();
            try {
                JsonObject jsonBook = gson.fromJson(strBook, JsonObject.class);
                book = gson.fromJson(jsonBook, Book.class);
            }catch (JsonParseException ignored){}

            int bookQty = book.getQuantity();
            if (bookQty == 0) {
                responseMessage.setStatusMessage(ResponseConstant.book_out_stoke);
                return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.BAD_REQUEST);
            }

            if(bookQty < bookNumber){
                responseMessage.setStatusMessage(bookQty+ ResponseConstant.book_out_available);
                return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.BAD_REQUEST);
            }

            double bookPrice = book.getPrice();
            double bankAmt = bankCard.getAmt();
            double totalCash = bookPrice * bookNumber;

            if (bankAmt < totalCash) {
                responseMessage.setStatusMessage(ResponseConstant.bankCard_insufficient_amt);
                return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.BAD_REQUEST);
            }

            bankAmt = bankAmt - bookPrice;
            bankCard.setAmt(bankAmt);
            bankCardRepo.save(bankCard);

            bookQty = bookQty - bookNumber;
            book.setQuantity(bookQty);
            myBook = bookRepo.save(book);

            return new ResponseEntity<>(gson.toJson(myBook), HttpStatus.OK);

        }

        return validateBook;
    }

}
