package com.gaji.app.mongo.repository;

import com.gaji.app.mongo.entity.ChatRoom;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

	String findBySellerId(Long sellerMemberSeq);

	String findByBuyerId(Long buyerMemberSeq);
	
}
