package com.gaji.app.mongo.repository;

import com.gaji.app.mongo.entity.ChatRoom;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

	@Query("{ '$or': [ { 'sellerId': ?0 }, { 'buyerId': ?0 } ] }")
    List<ChatRoom> findBySellerIdOrBuyerId(String userId);
	
}
