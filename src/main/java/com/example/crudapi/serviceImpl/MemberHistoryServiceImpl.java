package com.example.crudapi.serviceImpl;

import com.example.crudapi.datahandler.MemberDataHandler;
import com.example.crudapi.domain.Member;
import com.example.crudapi.domain.MemberHistory;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.repository.MemberHistoryRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
public class MemberHistoryServiceImpl {

    @Autowired
    MemberServiceImpl mberServiceImpl;
    @Autowired
    MemberDataHandler mberDataHandler;
    @Autowired
    MemberHistoryRepo mberHistoryRepo;


    ResponseEntity<?> response;
    MemberHistory mberHistory = new MemberHistory();
    Gson gson = new Gson();


    public ResponseEntity<?> mberHistoryRegister(Member member) {

        response = mberServiceImpl.memberRegister(member);

        if (response.getStatusCode() == HttpStatus.CREATED) {

            String status = ResponseConstant.mber_register;
            String date = Instant.now().toString();
            String strData = Objects.requireNonNull(response.getBody()).toString();

            mberHistory = prepareMemberHistory(status, date, strData);

            return new ResponseEntity<>(gson.toJson(mberHistory), HttpStatus.CREATED);
        }

        return response;
    }


    public ResponseEntity<?> updateMember(String smberId, String mberId,
                                          String mberName, String mberDob, double mberFees) {
        response = mberServiceImpl.updateMember(smberId, mberId, mberName, mberDob, mberFees);

        if (response.getStatusCode() == HttpStatus.OK) {

            String status = ResponseConstant.mber_update_success;
            String date = Instant.now().toString();
            String strData = Objects.requireNonNull(response.getBody()).toString();

            mberHistory = prepareMemberHistory(status, date, strData);

            return new ResponseEntity<>(gson.toJson(mberHistory), HttpStatus.OK);


        }
        return response;
    }

    //
    public ResponseEntity<?> deleteMember(String mberId) {

        response = mberServiceImpl.deleteMember(mberId);

        if (response.getStatusCode() == HttpStatus.OK) {

            String status = ResponseConstant.mber_delete_success;
            String date = Instant.now().toString();
            String strData = Objects.requireNonNull(response.getBody()).toString();

            mberHistory = prepareMemberHistory(status, date, strData);
            return new ResponseEntity<>(gson.toJson(mberHistory), HttpStatus.OK);

        }

        return response;
    }

    public MemberHistory prepareMemberHistory(String status, String dateTime, String data) {

        mberHistory.setStatus(status);
        mberHistory.setCreatedDateTime(dateTime);
        mberHistory.setData(data);
        mberHistory = mberHistoryRepo.save(mberHistory);

        return mberHistory;
    }

}
