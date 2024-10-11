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
	
	private String senderSeq;
	private String senderNickname;
	private String senderType;
	private String content;
	private String roomId;
	private LocalDateTime currentTime;
	
	public Message(String senderSeq, String senderNickname, String senderType, String content, String roomId, LocalDateTime currentTime) {
		this.senderSeq = senderSeq;
		this.senderNickname = senderNickname;
		this.senderType = senderType;
		this.content = content;
		this.roomId = roomId;
		this.currentTime = currentTime;
	}
	
}
