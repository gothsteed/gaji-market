package com.gaji.app.member.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.service.MemberService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.product.domain.Product;

@Controller
public class MemberController {
	
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

	@GetMapping("chatting")
    public String multichat() {
        return "chatting/multichat";
    }
	
    @GetMapping("/likeproduct")
    public ModelAndView likeProduct(ModelAndView mav, @AuthenticationPrincipal MemberUserDetail userDetail) {
        
    	Long memberSeq = userDetail.getMemberSeq();
    	 
    	List<Product> likedProducts = memberService.getLikedProductsForCurrentUser(memberSeq);
        
        mav.addObject("likedProducts", likedProducts);
        mav.setViewName("member/likeproduct");
        return mav;
    }
    
	
}
