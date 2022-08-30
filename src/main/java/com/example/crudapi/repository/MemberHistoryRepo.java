package com.example.crudapi.repository;


import com.example.crudapi.domain.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;


import javax.persistence.Id;

public interface MemberHistoryRepo extends JpaRepository<MemberHistory, Id> { }
