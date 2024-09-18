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
	
	private String sellerId;
	private String buyerId;
	private Long productSeq;
	
	public ChatRoom() {}
	
	public ChatRoom(String sellerId, String buyerId, Long productSeq) {
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.productSeq = productSeq;
	}
	
}
