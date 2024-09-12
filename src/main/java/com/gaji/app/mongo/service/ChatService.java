package com.gaji.app.mongo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gaji.app.mongo.entity.ChatRoom;
import com.gaji.app.mongo.repository.ChatRoomRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ChatService {

    private ChatRoomRepository chatRoomRepository;

	public ResponseEntity<String> createChatRoom(HttpServletRequest request, HttpServletResponse response, Long sellerMemberSeq,
			Long buyerMemberSeq, Long productSeq) {
		
		String sellerId = chatRoomRepository.findBySellerId(sellerMemberSeq);
		String buyerId = chatRoomRepository.findByBuyerId(buyerMemberSeq);
		
		chatRoomRepository.save(new ChatRoom(sellerId, buyerId, productSeq));
		
		return ResponseEntity.status(HttpStatus.OK).body("채팅방 생성되었습니다.");
	}

	

	

	

   
}
