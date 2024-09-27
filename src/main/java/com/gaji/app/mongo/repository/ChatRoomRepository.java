package com.gaji.app.mongo.repository;

import com.gaji.app.mongo.entity.ChatRoom;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

	List<ChatRoom> findBySellerMemberSeqAndBuyerMemberSeqAndProductSeq(Long sellerMemberSeq, Long buyerMemberSeq, Long productSeq);
	
	@Query("{ '$or': [ { 'sellerMemberSeq': ?0 }, { 'buyerMemberSeq': ?0 } ] }")
    List<ChatRoom> findBySellerMemberSeqOrBuyerMemberSeq(Long memberSeq);

}
