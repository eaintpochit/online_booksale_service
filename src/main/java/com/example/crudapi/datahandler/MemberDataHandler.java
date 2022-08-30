package com.example.crudapi.datahandler;

import com.example.crudapi.domain.Member;
import com.example.crudapi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MemberDataHandler {

    @Autowired
    MemberRepository mberRepo;

    Member mber = new Member();

    public Member validatMberRegister(String mberId, String mberNRC) {

        Member mberById = mberRepo.searchByMberId(mberId);
        Member mberByNRC = mberRepo.searchByMberNRC(mberNRC);

        if (mberById != null)
            return mberById;

        if (mberByNRC != null)
            return mberByNRC;

        return null;
    }

    public List<Member> findAllMber() {
        List<Member> mberList = mberRepo.findAll();

        return mberList == null ? null : mberList;
    }

    public Member searchMberById(String mberId) {

        if (!mberId.isEmpty())
            mber = mberRepo.searchByMberId(mberId);

        return mber == null ? null : mber;
    }


}
