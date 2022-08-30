package com.example.crudapi.serviceImpl;

import com.example.crudapi.datahandler.MemberDataHandler;
import com.example.crudapi.domain.PurchaseHistory;
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
import java.util.Objects;

@Component
public class BookPurchaseHistoryServiceImpl {


    @Autowired
    BankCardRepository bankCardRepo;
    @Autowired
    BookRepository bookRepo;
    @Autowired
    PurchaseHistoryRepository purchaseHisRepo;
    @Autowired
    BankCardServiceImpl bankCardServiceImpl;
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    MemberRepository mberRepo;
    @Autowired
    MemberDataHandler mberDataHandler;
    @Autowired
    BookPurchaseServiceImpl bookPurchaseServiceImpl;
    @Autowired
    ValidationforPurchaseBook validationforPurchaseBook;

    PurchaseHistory pHistory = new PurchaseHistory();
    Gson gson = new Gson();


    public ResponseEntity<?> PurchaseBookServiceHistory(String id, String cardNumber, int bookCount, String mberId) {
        ResponseEntity<?> response = bookPurchaseServiceImpl.bookPurchase(id, cardNumber, bookCount, mberId);

        if (response.getStatusCode() == HttpStatus.OK) {
            try{

            String getBody = Objects.requireNonNull(response.getBody()).toString();
            JsonObject jsonObject = gson.fromJson(getBody,JsonObject.class);
            pHistory = gson.fromJson(jsonObject, PurchaseHistory.class);

            }catch (JsonParseException ignored){}

            pHistory = purchaseHisRepo.save(pHistory);

            return new ResponseEntity<>(gson.toJson(pHistory), HttpStatus.OK);
        }
        return response;
    }
}
