package com.gaji.app.mongo.controller;

import java.util.List;

import com.gaji.app.mongo.dto.ChatRoomInfo;
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
        
        // 기존 채팅방 찾기 시도
        ResponseEntity<String> chatRoomResponse = chatService.findChatRoom(sellerMemberSeq, buyerMemberSeq, productSeq);
        String roomId = chatRoomResponse.getBody();
        
        // roomId가 없으면 새로운 채팅방 생성
        if (roomId == null) {
            ResponseEntity<String> createResponse = chatService.createChatRoom(request, response, sellerMemberSeq, buyerMemberSeq, productSeq);
            roomId = createResponse.getBody();
            
            if (createResponse.getStatusCode() != HttpStatus.OK || roomId == null) {
                ModelAndView errorMav = new ModelAndView("msg");
            
                errorMav.addObject("message", "채팅방 생성에 실패했습니다.");
                errorMav.addObject("loc", "/home");
                
                return errorMav;
            }
        }
        
        // 기존 또는 새로 생성된 roomId로 채팅 페이지 가져오기
        ModelAndView mav = chatService.getChatPage(userDetail, request, sellerMemberSeq, buyerMemberSeq, roomId, new ModelAndView());
        Member member = memberService.getInfo(buyerMemberSeq);
        mav.addObject("member", member);

        return mav;
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
        ResponseEntity<List<ChatRoomWithMessages>> chatRoomResponse = chatService.showChatRoom(request, response, memberSeq);
        List<ChatRoomWithMessages> chatRooms = chatRoomResponse.getBody();

        // 채팅방 목록이 있을 때만 추가
        if (chatRooms != null && !chatRooms.isEmpty()) {
            mav.addObject("chatRooms", chatRooms);
        }



        // WebSocket 연결에 필요한 정보 추가
        mav.addObject("loginUserSeq", userId); // 로그인한 사용자 ID

        mav.setViewName("chatting/chatList");
        return mav;
    }

    @GetMapping("/chatRoomInfo")
    public ResponseEntity<ChatRoomInfo> getChatRoomInfo(@RequestParam Long roomId) {
        // roomId를 통해 해당 채팅방의 정보를 가져옴
        ChatRoomInfo chatRoomInfo = chatService.getChatRoomInfo(roomId);
        return ResponseEntity.ok(chatRoomInfo);
    }
    
}
