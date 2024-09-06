package com.gaji.app.member.controller;

import com.gaji.app.common.FileManager;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.member.service.MyPageService;
import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.dto.ProductListDto;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String myPage(@RequestParam Long memberSeq, Model model) {
/*        if(memberSeq == null) {
            memberSeq = SecurityContextHolder.getContext().getAuthentication().getDetails()
        }*/
        model.addAttribute("memberSeq", memberSeq);
        return "member/mypage";
    }


    @GetMapping("/myPage/onSale")
    public String productList(Model model, @RequestParam Long memberSeq) {
        String name = memberService.getMemberName(memberSeq);
        model.addAttribute("memberName", name);
        model.addAttribute("memberSeq", memberSeq);
        return "product/memberOnSalePage";
    }
}
