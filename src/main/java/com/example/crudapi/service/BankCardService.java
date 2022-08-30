package com.example.crudapi.service;

import com.example.crudapi.domain.BankCard;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.serviceImpl.BankCardHistoryServiceImpl;
import com.example.crudapi.serviceImpl.ResponseMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BankCardService {
    @Autowired
    BankCardHistoryServiceImpl bankCardHisImpl;
    @Autowired
    ResponseMessageServiceImpl responseMsgServiceImpl;

    ResponseEntity<?> responseBankCard = null;
    ResponseMessage responseMessage = new ResponseMessage();


    public ResponseEntity<?> registerBankCard(BankCard bankCard) {
        responseBankCard = bankCardHisImpl.registerBankCardHistory(bankCard);
        if (responseBankCard.getStatusCode() == HttpStatus.CREATED) {
            String getBody = Objects.requireNonNull(responseBankCard.getBody()).toString();
            responseMessage = responseMsgServiceImpl.prepareBankCardResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        }
        return responseBankCard;
    }

    public ResponseEntity<?> deposit(double amt, String cardNumber) {

        responseBankCard = bankCardHisImpl.addDepositHis(amt, cardNumber);
        if (responseBankCard.getStatusCode() == HttpStatus.CREATED) {
            String getBody = Objects.requireNonNull(responseBankCard.getBody()).toString();
            responseMessage = responseMsgServiceImpl.prepareBankCardResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        return responseBankCard;
    }


}
