package com.gaji.app.mongo.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.common.FileManager;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.mongo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

    private ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("chatting")
    public ModelAndView multichat(@AuthenticationPrincipal MemberUserDetail userDetail,
                                  @RequestParam("fkMemberSeq") Long SellerMemberSeq,
                                  @RequestParam("productSeq") Long productSeq,
                                  ModelAndView mav) {

        Long BuyerMemberSeq = userDetail.getMemberSeq();

        System.out.println("확인용 바이어 : " + SellerMemberSeq);
        System.out.println("확인용 샐러 : " + BuyerMemberSeq);
        System.out.println("확인용 상품 : " + productSeq);

        // chatService.createChatRoom(request, response, schedule_seq);

        mav.addObject("SellerMemberSeq", SellerMemberSeq);
        mav.addObject("BuyerMemberSeq", BuyerMemberSeq);
        mav.addObject("productSeq", productSeq);

        mav.setViewName("chatting/multichat");

        return mav;
    }

}
