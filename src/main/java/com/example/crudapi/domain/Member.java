package com.example.crudapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;


@Entity
public class Member {

    @Id
    @Column
    private String memberId;
    @Column
    private String memberName;
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date memberDob;
    @Column
    private String memberNRC;
    @Column
    private double memberFees;
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date registerDate;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getMemberDob() {
        return memberDob;
    }

    public void setMemberDob(Date memberDob) {
        this.memberDob = memberDob;
    }

    public String getMemberNRC() {
        return memberNRC;
    }

    public void setMemberNRC(String memberNRC) {
        this.memberNRC = memberNRC;
    }

    public double getMemberFees() {
        return memberFees;
    }

    public void setMemberFees(double memberFees) {
        this.memberFees = memberFees;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
