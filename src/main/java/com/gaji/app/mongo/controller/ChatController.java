package com.gaji.app.mongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.mongo.entity.ChatRoom;
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
    
    @PostMapping("myChatting")
    public ResponseEntity<List<ChatRoom>> showChatRoom(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal MemberUserDetail userDetail) {
    	
    	String userId = userDetail.getUserId();
    	
    	return chatService.showChatRoom(request, response, userId);
    }
    
}
