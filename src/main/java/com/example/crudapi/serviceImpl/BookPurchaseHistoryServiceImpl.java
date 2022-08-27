package com.example.crudapi.serviceImpl;

import com.example.crudapi.datahandler.MemberDataHandler;
import com.example.crudapi.domain.BankCard;
import com.example.crudapi.domain.Book;
import com.example.crudapi.domain.Member;
import com.example.crudapi.domain.PurchaseHistory;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.BankCardRepository;
import com.example.crudapi.repository.BookRepository;
import com.example.crudapi.repository.MemberRepository;
import com.example.crudapi.repository.PurchaseHistoryRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

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

    PurchaseHistory pHistory = new PurchaseHistory();
    Book myBook = new Book();
    BankCard bankCard = new BankCard();
    ResponseMessage responseMessage = new ResponseMessage();
    Member mber = new Member();
    Gson gson = new Gson();


    public ResponseEntity<?> PurchaseBookServiceHistory(String id, String bookName, String author,
                                                        String cardNumber, int bookNumber, String mberName) {
        ResponseEntity<?> response = bookPurchaseServiceImpl.bookPurchase(id, bookName, author, cardNumber, bookNumber, mberName);
        if (response.getStatusCode() == HttpStatus.OK) {
            String getBody = response.getBody().toString();
            pHistory.setPurchaseBook(getBody);
            pHistory.setBankCardNumber(cardNumber);
            pHistory.setPurchaseDateTime(Instant.now().toString());
            pHistory = purchaseHisRepo.save(pHistory);
            return new ResponseEntity<>(gson.toJson(pHistory), HttpStatus.OK);
        }
        return response;
    }
}
