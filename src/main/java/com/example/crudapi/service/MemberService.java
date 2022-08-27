package com.example.crudapi.service;

import com.example.crudapi.domain.Member;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.serviceImpl.MemberHistoryServiceImpl;
import com.example.crudapi.serviceImpl.MemberServiceImpl;
import com.example.crudapi.serviceImpl.ResponseMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MemberService {

    @Autowired
    MemberHistoryServiceImpl mberHistoryServiceImpl;
    @Autowired
    MemberServiceImpl mberServiceImpl;
    @Autowired
    ResponseMessageServiceImpl responseMsgServiceImpl;

    ResponseEntity<?> response;
    ResponseMessage responseMessage = new ResponseMessage();

    public ResponseEntity<?> mberRegister(Member member) {

        response = mberHistoryServiceImpl.mberHistoryRegister(member);
        HttpStatus httpStatus = response.getStatusCode();

        if (httpStatus == HttpStatus.CREATED) {

            String getBody = Objects.requireNonNull(response.getBody()).toString();
            responseMessage = responseMsgServiceImpl.prepareMberResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);

        }

        return response;
    }

    public ResponseEntity<?> showAllMember() {
        return mberServiceImpl.showAllMember();
    }

    public ResponseEntity<?> findMberByUserInput(String mberId) {
        return mberServiceImpl.findMberByUserInput(mberId);
    }

    public ResponseEntity<?> updateMber(String smberId, String mberId, String mberName, String mberDob, double mberFees) {
        response = mberHistoryServiceImpl.updateMember(smberId, mberId, mberName, mberDob, mberFees);

        if (response.getStatusCode() == HttpStatus.OK) {

            String getBody = Objects.requireNonNull(response.getBody()).toString();
            responseMessage = responseMsgServiceImpl.prepareMberResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        }

        return response;
    }


    public ResponseEntity<?> deleteMember(String memberId) {
        response = mberHistoryServiceImpl.deleteMember(memberId);
        HttpStatus httpStatus = response.getStatusCode();

        if (httpStatus == HttpStatus.OK) {

            String getBody = Objects.requireNonNull(response.getBody()).toString();
            responseMessage = responseMsgServiceImpl.prepareMberResponseMessage(getBody);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        }

        return response;
    }


}
