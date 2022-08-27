package com.example.crudapi.service;

import com.example.crudapi.domain.Book;
import com.example.crudapi.domain.PurchaseHistory;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.BankCardRepository;
import com.example.crudapi.repository.BookRepository;
import com.example.crudapi.repository.PurchaseHistoryRepository;
import com.example.crudapi.serviceImpl.BookPurchaseHistoryServiceImpl;
import com.example.crudapi.serviceImpl.BookPurchaseServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class PurchaseBookService {


    @Autowired
    BankCardRepository bkCardRepo;
    @Autowired
    BookRepository bookRepo;
    @Autowired
    PurchaseHistoryRepository purhistoryRepo;
    @Autowired
    BookPurchaseServiceImpl pBookHisServiceImpl;
    @Autowired
    BookPurchaseHistoryServiceImpl bookPurchaseHisImpl;

    PurchaseHistory pHistory = new PurchaseHistory();
    ResponseMessage responseMessage = new ResponseMessage();
    Gson gson = new Gson();

    public ResponseEntity<?> purchaseBookService(String id, String bookName, String author,
                                                 String cardNumber, int bookNumber, String mberName) {
        Book book = new Book();
        ResponseEntity<?> response = bookPurchaseHisImpl.PurchaseBookServiceHistory(id, bookName, author, cardNumber, bookNumber, mberName);
        if (response.getStatusCode() == HttpStatus.OK) {
            String strBook = response.getBody().toString();
            try {
                JsonObject jsonObject = gson.fromJson(strBook, JsonObject.class);
                pHistory = gson.fromJson(jsonObject, PurchaseHistory.class);
                String getBook = jsonObject.get("purchaseBook").getAsString();
                JsonObject jsonBook = gson.fromJson(getBook, JsonObject.class);
                book = gson.fromJson(jsonBook, Book.class);
            } catch (JsonParseException ignored) {
            }

            responseMessage.setStatusMessage(ResponseConstant.purchase_book_success);
            responseMessage.setCreatd_date_time(pHistory.getPurchaseDateTime());
            responseMessage.setData(book);

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        return response;
    }


}
