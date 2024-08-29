package com.gaji.app.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaji.app.member.service.MemberService;

import net.minidev.json.JSONObject;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
	
    @GetMapping("memberregister")
    public String memberregister() {
        return "member/memberregister";
    }
    
    @ResponseBody
	@PostMapping(value="emailDuplicateCheck", produces="text/plain;charset=UTF-8")
	public String emailDuplicateCheck(String email) {
		
		String emailDuplicateCheck = null;
		try {
			emailDuplicateCheck = memberService.emailDuplicateCheck(email);
			// 회원등록시 입력한 이메일이 이미 있는 이메일인지 검사하는 메소드
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(emailDuplicateCheck);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("emailDuplicateCheck", emailDuplicateCheck);
		
		return jsonObj.toString();
	}
}