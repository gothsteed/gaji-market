package com.gaji.app.mongo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "messages")
public class Message {

	@Id
	private String _id;
	
	private String senderId;
	private String senderNickname;
	private String senderType;
	private String content;
	private String roomId;
	private LocalDateTime currentTime;
	
	public Message(String senderId, String senderNickname, String senderType, String content, String roomId, LocalDateTime currentTime) {
		this.senderId = senderId;
		this.senderNickname = senderNickname;
		this.senderType = senderType;
		this.content = content;
		this.roomId = roomId;
		this.currentTime = currentTime;
	}
	
}
