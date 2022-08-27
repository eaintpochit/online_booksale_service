package com.example.crudapi.serviceImpl;

import com.example.crudapi.domain.Book;
import com.example.crudapi.domain.BookHistory;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.repository.BookHistoryRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
public class BookHistoryServiceImpl {
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    BookHistoryRepository bookHisRepo;

    ResponseEntity<?> response;
    Gson gson = new Gson();
    BookHistory bookHistory = new BookHistory();

    public ResponseEntity<?> saveBookHistory(Book book) {

        response = bookServiceImpl.saveBook(book);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            String status = ResponseConstant.book_created;
            String dateTime = Instant.now().toString();
            String getData = Objects.requireNonNull(response.getBody()).toString();

            bookHistory = prepareBookHistory(status, dateTime, getData);
            bookHistory = bookHisRepo.save(bookHistory);

            return new ResponseEntity<>(gson.toJson(bookHistory), HttpStatus.CREATED);
        }

        return response;
    }

    public ResponseEntity<?> updateBookHis(String sId, String bkName, String author, double price,
                                           String publisher, int quantity) {

        response = bookServiceImpl.updateBookImpl(sId, bkName, author, price, publisher, quantity);

        if (response.getStatusCode() == HttpStatus.OK) {

            String status = ResponseConstant.book_update_success;
            String dateTime = Instant.now().toString();
            String getData = Objects.requireNonNull(response.getBody()).toString();

            bookHistory = prepareBookHistory(status, dateTime, getData);
            return new ResponseEntity<>(gson.toJson(bookHistory), HttpStatus.OK);

        }
        return response;

    }

    public ResponseEntity<?> deleteBook(String bkId) {

        response = bookServiceImpl.deleteBook(bkId);

        if (response.getStatusCode() == HttpStatus.OK) {
            String status = ResponseConstant.book_delete_success;
            String dateTime = Instant.now().toString();
            String getData = Objects.requireNonNull(response.getBody()).toString();

            bookHistory = prepareBookHistory(status, dateTime, getData);

            return new ResponseEntity<>(gson.toJson(bookHistory), HttpStatus.OK);

        }

        return response;

    }

    public BookHistory prepareBookHistory(String status, String dateTime, String data) {

        bookHistory.setStatus(status);
        bookHistory.setCreatedDateTime(dateTime);
        bookHistory.setData(data);
        bookHistory = bookHisRepo.save(bookHistory);
        return bookHistory;
    }


}
