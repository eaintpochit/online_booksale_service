package com.example.crudapi.repository;

import com.example.crudapi.domain.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Id> {

}
