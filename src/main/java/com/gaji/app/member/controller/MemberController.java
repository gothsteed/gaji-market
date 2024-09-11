package com.gaji.app.member.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.mongo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.service.MemberService;

@Controller
public class MemberController {

    private ChatService chatService;
	
	 private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
	
	@ResponseBody
	@GetMapping("insertkeyword")
	public ResponseEntity<Void> insertkeyword(@RequestParam String newKeyword, @AuthenticationPrincipal MemberUserDetail userDetail) {
		
		Long memberseq = userDetail.getMemberSeq();
		boolean isInserted = memberService.addKeyword(newKeyword, memberseq);
		
		if (isInserted) {
    		return ResponseEntity.ok().build();
    	} else {
    		return ResponseEntity.notFound().build();
    	}
	}
	
}
