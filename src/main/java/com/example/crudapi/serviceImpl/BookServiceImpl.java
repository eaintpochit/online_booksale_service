package com.example.crudapi.serviceImpl;

import com.example.crudapi.datahandler.BookDataHandler;
import com.example.crudapi.domain.Book;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.BookHistoryRepository;
import com.example.crudapi.repository.BookRepository;
import com.example.crudapi.service.BookLoaderService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class BookServiceImpl {

    @Autowired
    BookRepository bookRepo;
    @Autowired
    BookHistoryRepository opHisRepo;
    @Autowired
    BookDataHandler bookDataHandler;
    @Autowired
    BookLoaderService bookLoaderService;


    Book myBook = new Book();
    ResponseMessage responseMessage = new ResponseMessage();
    List<Book> bookList = new ArrayList<>();
    Gson gson = new Gson();

    public ResponseEntity<?> saveBook(Book requestBody) {

        String bookId = requestBody.getBookId();
        String author = requestBody.getAuthor();
        String bookName = requestBody.getBookName();

        try {

            myBook = bookDataHandler.valideSaveBook(bookId, author, bookName);

        } catch (NullPointerException ignored) {
        }

        if (myBook == null) {
            myBook = bookRepo.save(requestBody);
            return new ResponseEntity<>(gson.toJson(myBook), HttpStatus.CREATED);
        }

        responseMessage.setStatusMessage(ResponseConstant.book_search_exit);
        return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_IMPLEMENTED);


    }


    public ResponseEntity<?> showAllBook() {

        bookList = bookDataHandler.showAllBook();

        if (bookList.isEmpty()) {
            responseMessage.setStatusMessage(ResponseConstant.book_empty);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }
        Map<String, List<?>> map = new HashMap<>();
        map.put("Book", bookList);
        responseMessage.setData(map);
        return new ResponseEntity<>(responseMessage.getData(), HttpStatus.FOUND);

    }

    public ResponseEntity<?> showBookByInputData(String sId, String sbkName, String sauthor) {

        Object objBook = null;

        try {
            objBook = bookDataHandler.showBookByUserInputData(sId, sbkName, sauthor);
        } catch (NullPointerException ignored) {
        }

        if (objBook == null) {
            responseMessage.setStatusMessage(ResponseConstant.book_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Book", objBook);
        responseMessage.setData(map);
        return new ResponseEntity<>(responseMessage.getData(), HttpStatus.FOUND);


    }

    public ResponseEntity<?> updateBookImpl(String sId, String bkName, String author, double price,
                                            String publisher, int quantity) {

        try {

            myBook = bookRepo.searchById(sId);

        } catch (NullPointerException ignored) {
        }

        if (myBook == null) {
            responseMessage.setStatusMessage(ResponseConstant.book_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        if (!bkName.isEmpty())
            myBook.setBookName(bkName);

        if (!author.isEmpty())
            myBook.setAuthor(author);

        if (price != 0.00)
            myBook.setPrice(price);

        if (!publisher.isEmpty())
            myBook.setPublisher(publisher);
        if (quantity != 0)
            myBook.setQuantity(quantity);

        myBook = bookRepo.save(myBook);

        return new ResponseEntity<>(gson.toJson(myBook), HttpStatus.OK);

    }

    public ResponseEntity<?> deleteBook(String sId) {
        myBook = bookRepo.searchById(sId);

        if (myBook == null) {
            responseMessage.setStatusMessage(ResponseConstant.book_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        bookRepo.deleteBook(sId);
        return new ResponseEntity<>(gson.toJson(myBook), HttpStatus.OK);
    }

    public ResponseEntity<?> validateRequestBook(String id, String bookName, String author) {

        Object bookObj = null;

        try {
            bookObj = bookLoaderService.requestBook(id, bookName, author);
        } catch (NullPointerException ignored) {
        }

        if (bookObj == null) {
            responseMessage.setStatusMessage(ResponseConstant.book_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gson.toJson(bookObj), HttpStatus.FOUND);

    }

    public ResponseEntity<?> prepareBookforPurchase(String sId, String sbkName, String sauthor) {
        Object bookObj = null;
        try {
            bookObj = bookDataHandler.showBookByUserInputData(sId, sbkName, sauthor);
        } catch (NullPointerException ignored) {
        }

        if (bookObj == null) {
            responseMessage.setStatusMessage(ResponseConstant.book_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(bookObj), HttpStatus.FOUND);
    }


}
