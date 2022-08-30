package com.example.crudapi.serviceImpl;

import com.example.crudapi.datahandler.BankCardDataHandler;
import com.example.crudapi.datahandler.MemberDataHandler;
import com.example.crudapi.domain.BankCard;
import com.example.crudapi.domain.Book;
import com.example.crudapi.domain.Member;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.service.BookLoaderService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidationforPurchaseBook {

    @Autowired
    BookLoaderService bookLoaderService;
    @Autowired
    BankCardDataHandler bankCardDataHandler;
    @Autowired
    MemberDataHandler mberDataHandler;

    ResponseMessage responseMessage = new ResponseMessage();
    Gson gson = new Gson();
    public ResponseEntity<?> validateRequestBook(String id) {

        Book book = null;

        try {
            book = bookLoaderService.requestBook(id);
        } catch (NullPointerException ignored) {
        }

        if (book == null) {
            responseMessage.setStatusMessage(ResponseConstant.book_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gson.toJson(book), HttpStatus.FOUND);

    }

    public ResponseEntity<?> validateCardNumber(String cardNumber) {

        BankCard bankCard = new BankCard();

        try {
            bankCard = bankCardDataHandler.validateCardNumber(cardNumber);
        } catch (NullPointerException ignored) {
        }

        if (bankCard == null) {
            responseMessage.setStatusMessage(ResponseConstant.bankCard_number_invalid);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gson.toJson(bankCard), HttpStatus.FOUND);

    }

    public ResponseEntity<?> validateMber(String mberId) {

        Member mber = new Member();

        try {
            mber = mberDataHandler.searchMberById(mberId);
        } catch (NullPointerException ignored) {}

        if (mber == null) {
            responseMessage.setStatusMessage(ResponseConstant.mber_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gson.toJson(mber), HttpStatus.FOUND);
    }
}
