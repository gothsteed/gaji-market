package com.gaji.app.member.controller;

import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {

    private MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/api/v1/users/me/my-page-info")
    public ResponseEntity<MyPageDto> myPage() {
        MyPageDto mypage = memberService.getMyPageInfo();

        return ResponseEntity.ok(mypage);
    }

}
