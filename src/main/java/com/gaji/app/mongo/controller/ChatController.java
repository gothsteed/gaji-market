package com.gaji.app.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.mongo.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ChatController {

    private ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("chatting")
    public ModelAndView multichat(HttpServletRequest request, HttpServletResponse response,
    							  @AuthenticationPrincipal MemberUserDetail userDetail,
                                  @RequestParam("fkMemberSeq") Long SellerMemberSeq,
                                  @RequestParam("productSeq") Long productSeq,
                                  ModelAndView mav) {

        Long BuyerMemberSeq = userDetail.getMemberSeq();

        System.out.println("확인용 바이어 : " + SellerMemberSeq);
        System.out.println("확인용 샐러 : " + BuyerMemberSeq);
        System.out.println("확인용 상품 : " + productSeq);

        ResponseEntity<String> roomId = chatService.createChatRoom(request, response, SellerMemberSeq, BuyerMemberSeq, productSeq);

        mav.addObject("SellerMemberSeq", SellerMemberSeq);
        mav.addObject("BuyerMemberSeq", BuyerMemberSeq);
        mav.addObject("productSeq", productSeq);
        mav.addObject("roomId", roomId);

        mav.setViewName("chatting/multichat");

        return mav;
    }

}
