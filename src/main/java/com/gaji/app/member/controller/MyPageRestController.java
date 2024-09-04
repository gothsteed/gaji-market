package com.gaji.app.member.controller;

import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyPageRestController {

    private MemberService memberService;

    @Autowired
    public MyPageRestController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/api/v1/users/my-page-info")
    public ResponseEntity<MyPageDto> myPage(@RequestParam String userid) {
        MyPageDto mypage = memberService.getMyPageInfo(userid);

        return ResponseEntity.ok(mypage);
    }

}
