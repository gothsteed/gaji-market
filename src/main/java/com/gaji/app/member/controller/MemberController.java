package com.gaji.app.member.controller;

import com.gaji.app.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("mypage")
    public String mypage() {


        return "mypage/mypage";
    }

    @GetMapping("myedit")
    public String myedit() {


        return "mypage/myedit";
    }


}
