package com.gaji.app.mongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.mongo.entity.ChatRoom;
import com.gaji.app.mongo.repository.ChatRoomRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ChatService {

    private ChatRoomRepository chatRoomRepository;
    private MemberRepository memberRepository;
    
    public ChatService(ChatRoomRepository chatRoomRepository, MemberRepository memberRepository) {
    	this.chatRoomRepository = chatRoomRepository;
    	this.memberRepository = memberRepository;
    }
	
	public ResponseEntity<String> createChatRoom(HttpServletRequest request, HttpServletResponse response, 
        										 Long sellerMemberSeq, Long buyerMemberSeq, Long productSeq) {

	    Optional<Member> sellerChatRoom = memberRepository.findByMemberSeq(sellerMemberSeq);
	    Optional<Member> buyerChatRoom = memberRepository.findByMemberSeq(buyerMemberSeq);

	    if (sellerChatRoom.isPresent() && buyerChatRoom.isPresent()) {
	        String sellerId = sellerChatRoom.get().getUserId();
	        String buyerId = buyerChatRoom.get().getUserId();

	        ChatRoom chatRoom = new ChatRoom(sellerId, buyerId, productSeq);
	        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

	        String roomId = savedChatRoom.get_id();
	        return ResponseEntity.status(HttpStatus.OK).body(roomId.toString()); 
	    } 

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("채팅방 생성에 실패했습니다.");
	}

	public ModelAndView getChatPage(HttpServletRequest request, Long sellerMemberSeq, Long buyerMemberSeq,
									String roomId, String userId, ModelAndView mav) {
		
		Optional<Member> sellerChatRoom = memberRepository.findByMemberSeq(sellerMemberSeq);
	    Optional<Member> buyerChatRoom = memberRepository.findByMemberSeq(buyerMemberSeq);

	    if (sellerChatRoom.isPresent() && buyerChatRoom.isPresent()) {
	       mav.addObject("sellerId", sellerChatRoom.get().getUserId());
	       mav.addObject("buyerId", buyerChatRoom.get().getUserId());
	    } 
		
	    mav.addObject("roomId", roomId); 
	    mav.addObject("userId", userId); 
	    mav.setViewName("chatting/multichat");
		
		return mav;
	}

	public ResponseEntity<List<ChatRoom>> showChatRoom(HttpServletRequest request, HttpServletResponse response, String userId) {
        
		List<ChatRoom> roomList = chatRoomRepository.findBySellerIdOrBuyerId(userId);
        
		return ResponseEntity.ok().body(roomList);
    }

}
