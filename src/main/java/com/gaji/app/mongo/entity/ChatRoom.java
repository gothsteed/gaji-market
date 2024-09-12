package com.gaji.app.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "ChatRooms")
public class ChatRoom {

	@Id
	private String _id;
	
	private String sellerId;
	private String sellerNickname;
	private String buyerId;
	private String buyerNickname;
	private String name;
	private String content;
	
	public ChatRoom(String sellerId, String sellerNickname, String buyerId, String buyerNickname, String name, String content) {
		this.sellerId = sellerId;
		this.sellerNickname = sellerNickname;
		this.buyerId = buyerId;
		this.buyerNickname = buyerNickname;
		this.name = name;
		this.content = content;
	}
	
}
