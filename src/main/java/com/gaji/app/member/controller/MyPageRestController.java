package com.gaji.app.member.controller;

import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.member.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyPageRestController {

    private MyPageService myPageService;

    @Autowired
    public MyPageRestController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }


    @GetMapping("/api/v1/users/my-page-info")
    public ResponseEntity<MyPageDto> myPage(@RequestParam Long memberSeq) {
        MyPageDto mypage = myPageService.getMyPageInfo(memberSeq);

        return ResponseEntity.ok(mypage);
    }

}
