package com.example.crudapi.repository;

import com.example.crudapi.domain.BookHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface BookHistoryRepository extends JpaRepository<BookHistory, Id> {

}
