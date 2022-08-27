package com.example.crudapi.serviceImpl;

import com.example.crudapi.domain.BankCard;
import com.example.crudapi.domain.Book;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.BankCardRepository;
import com.example.crudapi.repository.BookRepository;
import com.example.crudapi.repository.PurchaseHistoryRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
    BookServiceImpl bookServiceImpl;
    @Autowired
    BankCardServiceImpl bankCardServiceImpl;
    @Autowired
    MemberServiceImpl mberServiceImpl;

    Book myBook = new Book();
    BankCard bankCard = new BankCard();
    ResponseMessage responseMessage = new ResponseMessage();
    Gson gson = new Gson();

    public ResponseEntity<?> bookPurchase(String id, String bookName, String author, String cardNumber,
                                          int bookNumber, String mberName) {

        ResponseEntity<?> validateBook = bookServiceImpl.validateRequestBook(id, bookName, author);
        if (validateBook.getStatusCode() == HttpStatus.FOUND) {
            String strBook = Objects.requireNonNull(validateBook.getBody()).toString();
            try {
                JsonObject jsonBook = gson.fromJson(strBook, JsonObject.class);
                myBook = gson.fromJson(jsonBook, Book.class);
            } catch (JsonParseException ignored) {
            }
        } else {
            return validateBook;
        }

        double bookPrice = myBook.getPrice();
        BankCard getBankCard = null;

        ResponseEntity<?> validateCardNumber = bankCardServiceImpl.validateCardNumber(cardNumber);
        ResponseEntity<?> mberResponse = mberServiceImpl.validateMber(mberName);
        if (mberResponse.getStatusCode() == HttpStatus.FOUND) {

            if (validateCardNumber.getStatusCode() == HttpStatus.FOUND) {
                String strBankCard = Objects.requireNonNull(validateCardNumber.getBody()).toString();
                try {
                    JsonObject jsonBankCard = gson.fromJson(strBankCard, JsonObject.class);
                    getBankCard = gson.fromJson(jsonBankCard, BankCard.class);
                } catch (JsonParseException ignored) {
                }

                assert getBankCard != null;
                double bankAmt = getBankCard.getAmt();
                double totalCash = bookPrice * bookNumber;

                if (bankAmt < totalCash) {
                    responseMessage.setStatusMessage(ResponseConstant.bankCard_insufficient_amt);
                    return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.BAD_REQUEST);
                }


                bankAmt = bankAmt - bookPrice;
                getBankCard.setAmt(bankAmt);
                bankCard = bankCardRepo.save(getBankCard);


                int bookQty = myBook.getQuantity();
                if (bookQty == 0 || bookQty < bookNumber) {
                    responseMessage.setStatusMessage(ResponseConstant.book_out_stoke);
                    return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.OK);
                }
                bookQty = bookQty - bookNumber;
                myBook.setQuantity(bookQty);
                myBook = bookRepo.save(myBook);

                return new ResponseEntity<>(gson.toJson(myBook), HttpStatus.OK);
            }
            return validateCardNumber;
        }

        return mberResponse;

    }

}
