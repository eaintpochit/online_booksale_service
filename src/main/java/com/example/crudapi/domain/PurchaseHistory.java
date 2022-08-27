package com.example.crudapi.domain;

import javax.persistence.*;

@Entity
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long pid;
    @Column
    private String memberName;
    @Column
    private String purchaseBook;
    @Column
    private String bankCardNumber;
    @Column
    private String purchaseDateTime;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPurchaseBook() {
        return purchaseBook;
    }

    public void setPurchaseBook(String purchaseBook) {
        this.purchaseBook = purchaseBook;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(String purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }
}
