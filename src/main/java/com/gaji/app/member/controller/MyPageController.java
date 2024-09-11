package com.gaji.app.member.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.member.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberService memberService;

    @Autowired
    public MyPageController(MyPageService myPageService, MemberService memberService) {
        this.myPageService = myPageService;
        this.memberService = memberService;
    }

    @GetMapping("/myPage")
    public String myPage(@RequestParam(required = false) Long memberSeq, Model model) {

        if (memberSeq == null) {
            // 현재 로그인된 사용자 정보를 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MemberUserDetail userDetail = (MemberUserDetail) authentication.getPrincipal();
            memberSeq = userDetail.getMemberSeq();
        }

        model.addAttribute("memberSeq", memberSeq);
        return "member/mypage";
    }


    @GetMapping("/myPage/onSale")
    public String productList(Model model, @RequestParam Long memberSeq) {
        String name = memberService.getMemberName(memberSeq);
        model.addAttribute("title", name + "의 판매 상품");
        model.addAttribute("memberName", name);
        model.addAttribute("memberSeq", memberSeq);
        return "product/myPageOnSale";
    }
}
