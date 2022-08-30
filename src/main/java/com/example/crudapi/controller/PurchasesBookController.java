package com.example.crudapi.controller;

import com.example.crudapi.service.BookLoaderService;
import com.example.crudapi.service.PurchaseBookService;
import com.example.crudapi.serviceImpl.BookPurchaseHistoryServiceImpl;
import com.example.crudapi.serviceImpl.BookPurchaseServiceImpl;
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

    @RequestMapping("/purchaseBook")
    public ResponseEntity<?> bookPurchase(@RequestParam(name = "bookId", required = false) String id,
                                          @RequestParam(name = "cardNumber") String cardNumber,
                                          @RequestParam(name = "bookNumber") int bookNumber,
                                          @RequestParam(name = "memberId") String mberId) {

        return purchaseBKService.purchaseBookService(id, cardNumber, bookNumber, mberId);
    }

}
