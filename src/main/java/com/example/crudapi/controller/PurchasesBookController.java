package com.example.crudapi.controller;

import com.example.crudapi.service.BookLoaderService;
import com.example.crudapi.service.PurchaseBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class PurchasesBookController {
    @Autowired
    PurchaseBookService purchaseBKService;
    @Autowired
    BookLoaderService bookLoaderService;
    @Autowired
    RestTemplate restTemplate;


    @RequestMapping("/purchaseBook")
    public ResponseEntity<?> bookPurchase(@RequestParam(name = "bookId", required = false) String id,
                                          @RequestParam(name = "bookName", required = false) String bkName,
                                          @RequestParam(name = "author", required = false) String author,
                                          @RequestParam(name = "cardNumber") String cardNumber,
                                          @RequestParam(name = "bookNumber") int bookNumber,
                                          @RequestParam(name = "mberName") String mberName) {

        return purchaseBKService.purchaseBookService(id, bkName, author, cardNumber, bookNumber, mberName);
    }

    @RequestMapping("/getBook")
    public Object getRequestBook(@RequestParam(name = "bookId", required = false) String id,
                                 @RequestParam(name = "bookName", required = false) String bkName,
                                 @RequestParam(name = "author", required = false) String author) {

        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> http = new HttpEntity(header);
//        String getData = null;


        Object getData = restTemplate.exchange("http://localhost:8080/preparebookforpurchase?bookId={bookId}&bookName={bookName}&author={author}", HttpMethod.GET, http, Object.class, id, bkName, author).getBody();
//      return bookLoaderService.requestBook(id, bkName, author);
        return getData;
    }

}
