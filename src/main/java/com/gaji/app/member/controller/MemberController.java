package com.gaji.app.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	@GetMapping("chatting")
    public String multichat() {
        return "chatting/multichat";
    }
	
}
