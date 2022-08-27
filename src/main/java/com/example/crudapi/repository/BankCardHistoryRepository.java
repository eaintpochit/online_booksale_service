package com.example.crudapi.repository;

import com.example.crudapi.domain.BankCardHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface BankCardHistoryRepository extends JpaRepository<BankCardHistory, Id> {
}
