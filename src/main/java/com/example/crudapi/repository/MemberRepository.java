package com.example.crudapi.repository;


import com.example.crudapi.domain.Member;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Id> {

    @Transactional
    @Modifying
    @Query("delete from Member m where m.memberId = :memberId")
    void deleteMemberById(@Param("memberId") String memberId);

    @Query("select m from Member m where m.memberId = :memberId")
    Member searchByMberId(@Param("memberId") String memberId);

    @Query("select m from Member m where m.memberName = :memberName")
    Member searchByMberName(@Param("memberName") String memberName);

    @Query("select m from Member m where m.memberNRC = :memberNRC")
    Member searchByMberNRC(@Param("memberNRC") String memberNRC);

}
