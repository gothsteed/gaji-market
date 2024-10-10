package com.gaji.app.mongo.repository;

import com.gaji.app.mongo.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    Optional<ChatRoom> findById(String roomId);

    @Query("{ '$or': [ { 'sellerId': ?0 }, { 'buyerId': ?0 } ] }")
    List<ChatRoom> findBySellerIdOrBuyerId(String userId);
	List<ChatRoom> findBySellerMemberSeqAndBuyerMemberSeqAndProductSeq(Long sellerMemberSeq, Long buyerMemberSeq, Long productSeq);
	
	@Query("{ '$or': [ { 'sellerMemberSeq': ?0 }, { 'buyerMemberSeq': ?0 } ] }")
    List<ChatRoom> findBySellerMemberSeqOrBuyerMemberSeq(Long memberSeq);


}
