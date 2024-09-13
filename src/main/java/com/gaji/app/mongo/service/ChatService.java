package com.gaji.app.mongo.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	public ResponseEntity<String> createChatRoom(HttpServletRequest request, HttpServletResponse response, Long sellerMemberSeq,
			Long buyerMemberSeq, Long productSeq) {
		
		Optional<Member> sellerChatRoom = memberRepository.findByMemberSeq(sellerMemberSeq);
	    Optional<Member> buyerChatRoom = memberRepository.findByMemberSeq(buyerMemberSeq);

	    if (sellerChatRoom.isPresent() && buyerChatRoom.isPresent()) {
	        String sellerId = sellerChatRoom.get().getUserId();
	        String buyerId = buyerChatRoom.get().getUserId();

	        ChatRoom chatRoom = new ChatRoom(sellerId, buyerId, productSeq);
	        chatRoomRepository.save(chatRoom);
	    } 
		
		return ResponseEntity.status(HttpStatus.OK).body("채팅방이 생성되었습니다.");
	}

	

	

	

   
}
