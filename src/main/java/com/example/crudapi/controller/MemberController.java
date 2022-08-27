package com.example.crudapi.controller;

import com.example.crudapi.domain.Member;
import com.example.crudapi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    MemberService mberService;

    @PostMapping(value = "/memberRegister", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerMember(@RequestBody Member mber) {
        return mberService.mberRegister(mber);
    }

    @GetMapping(value = "/showAllMember")
    public ResponseEntity<?> showAllMember() {
        return mberService.showAllMember();
    }


    @GetMapping(value = "/findMberByuserInput")
    public ResponseEntity<?> findMberByUserInput(@RequestParam(name = "memberId", required = false) String mberId) {
        return mberService.findMberByUserInput(mberId);
    }

    //
    @PutMapping(value = "/upDateMember")
    public ResponseEntity<?> updateMember(@RequestParam(name = "smemberId") String smberId,
                                          @RequestParam(name = "memberId") String mberId,
                                          @RequestParam(name = "memberName") String mberName,
                                          @RequestParam(name = "memberDob") String mberDob,
                                          @RequestParam(name = "memberFees") double mberFees) {
        return mberService.updateMber(smberId, mberId, mberName, mberDob, mberFees);
    }

    @DeleteMapping(value = "/deleteMemberById")
    public ResponseEntity<?> deleteMember(@RequestParam(name = "memberId") String mberId) {
        return mberService.deleteMember(mberId);
    }
}
