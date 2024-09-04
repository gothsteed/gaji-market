package com.gaji.app.member.controller;

import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.service.MemberService;
import java.io.File;
import java.util.List;

import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    private MemberService memberService;
	private FileManager fileManager;


    @Autowired
    public MemberController(MemberService memberService, FileManager fileManager) {
        this.memberService = memberService;
		this.fileManager = fileManager;
    }


	@GetMapping("chatting")
    public String multichat() {
        return "chatting/multichat";
    }
	
    @GetMapping("/likeproduct")
    public String likeproduct() {
        return "member/likeproduct";
    }

	
}
