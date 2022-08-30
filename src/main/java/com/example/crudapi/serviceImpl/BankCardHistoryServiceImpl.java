package com.example.crudapi.serviceImpl;

import com.example.crudapi.domain.BankCard;
import com.example.crudapi.domain.BankCardHistory;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.repository.BankCardHistoryRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
public class BankCardHistoryServiceImpl {

    @Autowired
    BankCardServiceImpl bankCardServiceImpl;
    @Autowired
    BankCardHistoryRepository bankCardHisRepo;

    ResponseEntity<?> response;
    Gson gson = new Gson();
    BankCardHistory bankCardHistory = new BankCardHistory();

    public ResponseEntity<?> registerBankCardHistory(BankCard bankCard) {

        response = bankCardServiceImpl.registerBankCard(bankCard);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            String getBody = Objects.requireNonNull(response.getBody()).toString();
            String status = ResponseConstant.bankCard_register;
            String dateTime = Instant.now().toString();
            bankCardHistory = prepareBankHistory(status, dateTime, getBody);

            return new ResponseEntity<>(gson.toJson(bankCardHistory), HttpStatus.CREATED);

        }
        return response;

    }

    public ResponseEntity<?> addDepositHis(double amt, String cardNumber) {
        response = bankCardServiceImpl.addDeposit(amt, cardNumber);
        if (response.getStatusCode() == HttpStatus.OK) {
            String getBody = Objects.requireNonNull(response.getBody()).toString();
            String status = ResponseConstant.bankCard_Deposit_Success;
            String dateTime = Instant.now().toString();
            bankCardHistory = prepareBankHistory(status, dateTime, getBody);

            return new ResponseEntity<>(gson.toJson(bankCardHistory), HttpStatus.CREATED);
        }
        return response;
    }


    public BankCardHistory prepareBankHistory(String status, String dateTime, String data) {

        bankCardHistory.setStatus(status);
        bankCardHistory.setCreatedDateTime(dateTime);
        bankCardHistory.setData(data);
        bankCardHistory = bankCardHisRepo.save(bankCardHistory);

        return bankCardHistory;
    }
}
