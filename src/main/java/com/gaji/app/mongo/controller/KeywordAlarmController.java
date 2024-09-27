package com.gaji.app.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class KeywordAlarmController {

    //private MemberService memberService;
    //
    //@Autowired
    //public KeywordAlarmController(MemberService memberService) {
    //    this.memberService = memberService;
    //}
    //
    //@ResponseBody
    //@GetMapping("/keywordalarm")
    //public ModelAndView createAndRedirectToChatPage(HttpServletRequest request, HttpServletResponse response, 
	//									            @AuthenticationPrincipal MemberUserDetail userDetail,
	//									            @RequestParam String memberSeq) {
    //
    //    return null;
    //}
    
}
