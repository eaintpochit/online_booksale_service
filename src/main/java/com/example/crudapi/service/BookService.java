package com.example.crudapi.service;

import com.example.crudapi.datahandler.BookDataHandler;
import com.example.crudapi.domain.Book;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.BookHistoryRepository;
import com.example.crudapi.repository.BookRepository;
import com.example.crudapi.serviceImpl.BookHistoryServiceImpl;
import com.example.crudapi.serviceImpl.BookServiceImpl;
import com.example.crudapi.serviceImpl.ResponseMessageServiceImpl;
import com.example.crudapi.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepo;
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    BookDataHandler bookdatahandler;
    @Autowired
    DateTimeUtil dateUtil;
    @Autowired
    BookHistoryRepository bookHisRepo;
    @Autowired
    BookHistoryServiceImpl bookHisServiceImpl;
    @Autowired
    ResponseMessageServiceImpl responseMsgServiceImpl;

    ResponseMessage responseMessage = new ResponseMessage();


    ResponseEntity<?> response;

    public ResponseEntity<?> saveBook(Book requestBook) {
        response = bookHisServiceImpl.saveBookHistory(requestBook);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            String getBody = Objects.requireNonNull(response.getBody()).toString();
            responseMessage = responseMsgServiceImpl.prepareBookResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        }
        return response;
    }


    public ResponseEntity<?> showAllBook() {

        return bookServiceImpl.showAllBook();

    }


    public ResponseEntity<?> updateBook(String sId, String bkName, String author, double price,
                                        String publisher, int quantity) {

        response = bookHisServiceImpl.updateBookHis(sId, bkName, author, price, publisher, quantity);
        String getBody = Objects.requireNonNull(response.getBody()).toString();
        HttpStatus httpStatus = response.getStatusCode();
        if (httpStatus == HttpStatus.OK) {
            responseMessage = responseMsgServiceImpl.prepareBookResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }

        return response;

    }


    public ResponseEntity<?> deleteBook(String bkId) {

        response = bookHisServiceImpl.deleteBook(bkId);
        String getBody = Objects.requireNonNull(response.getBody()).toString();
        HttpStatus httpStatus = response.getStatusCode();
        if (httpStatus == HttpStatus.OK) {
            responseMessage = responseMsgServiceImpl.prepareBookResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        return response;
    }


    public ResponseEntity<?> searchDataByUserInput(String id, String bkName, String author) {

        return bookServiceImpl.showBookByInputData(id, bkName, author);

    }

    public ResponseEntity<?> prepareBookforPurchase(String sbookId) {
        return bookServiceImpl.prepareBookforPurchase(sbookId);
    }


}
