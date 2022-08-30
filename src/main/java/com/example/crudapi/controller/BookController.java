package com.example.crudapi.controller;

import com.example.crudapi.domain.Book;
import com.example.crudapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class BookController {
    @Autowired
    BookService bkService;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/saveBook", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveBookData(@RequestBody Book bk) {
        return bkService.saveBook(bk);
    }


    @GetMapping(value = "/showAllBook")
    public ResponseEntity<?> showAllBook() {
        return bkService.showAllBook();
    }


    @GetMapping("/showBookByInputData")
    public ResponseEntity<?> showBookData(@RequestParam(value = "bookId", required = false) String id,
                                          @RequestParam(name = "bookName", required = false) String bkName,
                                          @RequestParam(name = "author", required = false) String author) {

        return bkService.searchDataByUserInput(id, bkName, author);

    }


    @PutMapping(value = "/updateBook", produces = "application/json")
    public ResponseEntity<?> updateBook(@RequestParam(name = "sid", required = false) String sId,
                                        @RequestParam(name = "bookName", required = false) String bkName,
                                        @RequestParam(name = "author", required = false) String author,
                                        @RequestParam(name = "price", required = false) double price,
                                        @RequestParam(name = "publisher", required = false) String publisher,
                                        @RequestParam(name = "quantity", required = false) int quantity) {

        return bkService.updateBook(sId, bkName, author, price, publisher, quantity);

    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<?> deleteBook(@RequestParam("bookId") String bkId) {

        return bkService.deleteBook(bkId);

    }

    @GetMapping("/preparebookforsale")
    public ResponseEntity<?> prepreBookforsale(@RequestParam(value = "bookId", required = false) String id) {
        return bkService.prepareBookforPurchase(id);
    }


}