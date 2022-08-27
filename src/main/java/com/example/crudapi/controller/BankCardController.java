package com.example.crudapi.controller;

import com.example.crudapi.domain.BankCard;
import com.example.crudapi.service.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BankCardController {

    @Autowired
    BankCardService bankCardService;


    @PostMapping(value = "/registerBankCard")
    public ResponseEntity<?> registerBankCard(@RequestBody BankCard bankCard) {
        return bankCardService.registerBankCard(bankCard);
    }

    @PutMapping("/addDeposit")
    public ResponseEntity addDeposit(@RequestParam(name = "amt") double depositAmt,
                                     @RequestParam(name = "cardNumber") String cardNumber) {
        return bankCardService.deposit(depositAmt, cardNumber);
    }

}
