package com.example.crudapi.datahandler;


import com.example.crudapi.domain.BankCard;
import com.example.crudapi.repository.BankCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankCardDataHandler {

    @Autowired
    BankCardRepository bandCardRepo;

    BankCard bankCard = new BankCard();

    public BankCard validateSaveBankCard(String nrc, String cardNumber) {

        BankCard bankCardByNrc = bandCardRepo.findByNrc(nrc);
        BankCard bankCardByCardNumber = bandCardRepo.findByCardNumber(cardNumber);

        if (bankCardByNrc != null)
            return bankCardByNrc;
        if (bankCardByCardNumber != null)
            return bankCardByCardNumber;

        return null;

    }

    public BankCard validateCardNumber(String cardNumber) {
        bankCard = bandCardRepo.findByCardNumber(cardNumber);
        return bankCard == null ? null : bankCard;
    }
}
