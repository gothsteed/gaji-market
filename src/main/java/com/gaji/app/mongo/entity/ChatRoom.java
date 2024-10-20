package com.gaji.app.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "chatRooms")
public class ChatRoom {

	@Id
	private String _id;
	
	private Long sellerMemberSeq;
	private Long buyerMemberSeq;
	private Long productSeq;
	
	public ChatRoom() {}
	
	public ChatRoom(Long sellerMemberSeq, Long buyerMemberSeq, Long productSeq) {
		this.sellerMemberSeq = sellerMemberSeq;
		this.buyerMemberSeq = buyerMemberSeq;
		this.productSeq = productSeq;
	}
	
}
