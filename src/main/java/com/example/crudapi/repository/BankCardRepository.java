package com.example.crudapi.repository;

import com.example.crudapi.domain.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Id;

public interface BankCardRepository extends JpaRepository<BankCard, Id> {

    @Query("select bk from BankCard bk where bk.cardNumber = :cardNumber")
    BankCard findByCardNumber(@Param("cardNumber") String cardNumber);

    @Query("select bk from BankCard bk where bk.nrc = :nrc")
    BankCard findByNrc(@Param("nrc") String nrc);
}
