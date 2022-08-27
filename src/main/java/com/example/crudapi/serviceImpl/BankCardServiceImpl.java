package com.example.crudapi.serviceImpl;

import com.example.crudapi.datahandler.BankCardDataHandler;
import com.example.crudapi.domain.BankCard;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.BankCardRepository;
import com.example.crudapi.util.DateTimeUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class BankCardServiceImpl {

    @Autowired
    BankCardRepository bankCardRepo;
    @Autowired
    DateTimeUtil dateUtil;
    @Autowired
    BankCardDataHandler bankCardDataHandler;

    BankCard myBankCard = new BankCard();
    ResponseMessage responseMessage = new ResponseMessage();
    Gson gson = new Gson();


    public ResponseEntity<?> registerBankCard(BankCard bankCard) {

        String bankCardNrc = bankCard.getNrc();
        String bankCardNumber = bankCard.getCardNumber();

        try {
            myBankCard = bankCardDataHandler.validateSaveBankCard(bankCardNrc, bankCardNumber);
        } catch (NullPointerException ignored) {
        }

        if (myBankCard != null) {
            if (myBankCard.getNrc().equals(bankCardNrc))
                responseMessage.setStatusMessage(ResponseConstant.bankCard_Nrc_Same);
            if (myBankCard.getCardNumber().equals(bankCardNumber)) {
                responseMessage.setStatusMessage(ResponseConstant.bankCard_Number_Same);
            }

            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.FOUND);
        }

        myBankCard = bankCardRepo.save(bankCard);

        return new ResponseEntity<>(gson.toJson(myBankCard), HttpStatus.CREATED);
    }

    public ResponseEntity<?> addDeposit(double amt, String cardNumber) {

        try {
            myBankCard = bankCardDataHandler.validateCardNumber(cardNumber);
        } catch (NullPointerException ignored) {
        }

        if (myBankCard == null) {
            responseMessage.setStatusMessage(ResponseConstant.bankCard_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }
        double deposit = myBankCard.getAmt();
        deposit = deposit + amt;
        myBankCard.setAmt(deposit);
        myBankCard = bankCardRepo.save(myBankCard);

        return new ResponseEntity<>(gson.toJson(myBankCard), HttpStatus.OK);
    }


    public ResponseEntity<?> validateCardNumber(String cardNumber) {

        try {
            myBankCard = bankCardDataHandler.validateCardNumber(cardNumber);
        } catch (NullPointerException ignored) {
        }

        if (myBankCard == null) {
            responseMessage.setStatusMessage(ResponseConstant.bankCard_number_invalid);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gson.toJson(myBankCard), HttpStatus.FOUND);

    }

}
