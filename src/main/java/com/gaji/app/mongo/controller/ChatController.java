package com.gaji.app.mongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.mongo.dto.ChatRoomWithMessages;
import com.gaji.app.mongo.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ChatController {

    private ChatService chatService;
    private MemberService memberService;

    @Autowired
    public ChatController(ChatService chatService, MemberService memberService) {
        this.chatService = chatService;
        this.memberService = memberService;
    }
    
    @GetMapping("chatting")
    public ModelAndView createAndRedirectToChatPage(HttpServletRequest request, HttpServletResponse response, 
										            @AuthenticationPrincipal MemberUserDetail userDetail,
										            @RequestParam("fkMemberSeq") Long sellerMemberSeq,
										            @RequestParam("productSeq") Long productSeq) {

        Long buyerMemberSeq = userDetail.getMemberSeq();
        String userId = userDetail.getUserId();
        
        ResponseEntity<String> responseEntity = chatService.createChatRoom(request, response, sellerMemberSeq, buyerMemberSeq, productSeq);
        String roomId = responseEntity.getBody();

        if (responseEntity.getStatusCode() == HttpStatus.OK && roomId != null) {
        	ModelAndView mav = new ModelAndView();
            mav = chatService.getChatPage(request, sellerMemberSeq, buyerMemberSeq, roomId, userId, mav);
            
            return mav;
        }
        
        ModelAndView errorMav = new ModelAndView("msg");
        errorMav.addObject("message", "채팅방 생성에 실패했습니다.");
        errorMav.addObject("loc", "/home");
        
        return errorMav;
    }
    
    @GetMapping("myChatting")
    public ModelAndView showChatRoom(HttpServletRequest request, HttpServletResponse response, 
                                     @AuthenticationPrincipal MemberUserDetail userDetail, ModelAndView mav) {
        
        Long memberSeq = userDetail.getMemberSeq();
        String userId = userDetail.getUserId();
        
        // 사용자 정보 가져오기
        Member member = memberService.getInfo(memberSeq);
        mav.addObject("member", member);
        
        // 채팅방 목록 가져오기
        ResponseEntity<List<ChatRoomWithMessages>> chatRoomResponse = chatService.showChatRoom(request, response, userId);
        List<ChatRoomWithMessages> chatRooms = chatRoomResponse.getBody();
        mav.addObject("chatRooms", chatRooms);
        
        mav.setViewName("chatting/multichat");
        
        return mav;
    }
    
}
