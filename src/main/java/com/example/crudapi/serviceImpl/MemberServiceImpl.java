package com.example.crudapi.serviceImpl;

import com.example.crudapi.datahandler.MemberDataHandler;
import com.example.crudapi.domain.Member;
import com.example.crudapi.domain.ResponseConstant;
import com.example.crudapi.model.ResponseMessage;
import com.example.crudapi.repository.MemberRepository;
import com.example.crudapi.util.DateTimeUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemberServiceImpl {

    @Autowired
    DateTimeUtil dateUtil;
    @Autowired
    MemberRepository mberRepo;
    @Autowired
    MemberDataHandler memberDataHandler;

    Member mber = new Member();
    ResponseMessage responseMessage = new ResponseMessage();
    Gson gson = new Gson();


    public ResponseEntity<?> memberRegister(Member member) {

        String mberId = member.getMemberId();
        String mberNRC = member.getMemberNRC();

        try {

            mber = memberDataHandler.validatMberRegister(mberId, mberNRC);

        } catch (NullPointerException ignored) {
        }

        if (mber != null) {
            responseMessage.setStatusMessage(ResponseConstant.mber_search_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_IMPLEMENTED);
        }

        Date memRegisterDate = dateUtil.getCurrentDateTime();
        member.setRegisterDate(memRegisterDate);
        mber = mberRepo.save(member);

        return new ResponseEntity<>(gson.toJson(mber), HttpStatus.CREATED);


    }

    public ResponseEntity<?> showAllMember() {

        List<Member> mberList = memberDataHandler.findAllMerber();

        if (mberList.isEmpty()) {
            responseMessage.setStatusMessage(ResponseConstant.mber_empty);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        Map<String, List<?>> map = new HashMap<>();
        map.put("Member", mberList);
        responseMessage.setData(map);
        return new ResponseEntity<>(responseMessage.getData(), HttpStatus.FOUND);

    }


    public ResponseEntity<?> updateMember(String smberId, String mberId, String mberName, String mberDob, double mberFees) {
        try {
            mber = memberDataHandler.searchMemberByUserInput(smberId);
        } catch (NullPointerException ignored) {
        }

        if (mber == null) {
            responseMessage.setStatusMessage(ResponseConstant.mber_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }
        if (!mberId.isEmpty())
            mber.setMemberId(mberId);
        if (!mberName.isEmpty())
            mber.setMemberName(mberName);
        if (!mberDob.isEmpty()) {
            Date date = dateUtil.dobFormat(mberDob);
            mber.setMemberDob(date);
        }
        if (mberFees != 0.00)
            mber.setMemberFees(mberFees);

        mber = mberRepo.save(mber);
        return new ResponseEntity<>(gson.toJson(mber), HttpStatus.OK);
    }

    public ResponseEntity<?> findMberByUserInput(String mberId) {

        try {
            mber = memberDataHandler.searchMemberByUserInput(mberId);
        } catch (NullPointerException ignored) {
        }

        if (mber == null) {

            responseMessage.setStatusMessage(ResponseConstant.mber_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);

        }

        Map<String, Member> map = new HashMap<>();
        map.put("Member", mber);
        responseMessage.setData(map);

        return new ResponseEntity<>(responseMessage.getData(), HttpStatus.FOUND);

    }

    public ResponseEntity<?> deleteMember(String mberId) {

        try {
            mber = mberRepo.searchByMberId(mberId);
        } catch (NullPointerException ignored) {
        }

        if (mber == null) {
            responseMessage.setStatusMessage(ResponseConstant.mber_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        mberRepo.deleteMemberById(mberId);

        return new ResponseEntity<>(gson.toJson(mber), HttpStatus.OK);
    }

    public ResponseEntity<?> validateMber(String mberName) {
        try {
            mber = memberDataHandler.searchMberByMberName(mberName);
        } catch (NullPointerException ignored) {
        }

        if (mber == null) {
            responseMessage.setStatusMessage(ResponseConstant.mber_search_no_exit);
            return new ResponseEntity<>(responseMessage.getStatusMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gson.toJson(mber), HttpStatus.FOUND);
    }

}
