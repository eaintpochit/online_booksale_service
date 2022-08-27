package com.example.crudapi.repository;

import com.example.crudapi.domain.Member;
import com.example.crudapi.domain.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Id;

public interface MemberHistoryRepo extends JpaRepository<MemberHistory, Id> {

    @Query("select m from MemberHistory m where m.data = :data")
    Member searchByMberId(@Param("data") String data);

//    @Query("select m from Member m where m.memberName = :memberName")
//    Member searchByMberName(@Param("memberName") String memberName);

}
