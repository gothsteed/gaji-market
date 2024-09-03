package com.gaji.app.member.controller;

import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/myPage")
    public String myPage() {

        return "member/mypage";
    }
}
