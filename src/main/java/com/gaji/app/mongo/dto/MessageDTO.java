package com.gaji.app.mongo.dto;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

	private String senderId;
	private String senderNickname;
	private String senderType;
	private String content;
	
	public static MessageDTO convertMessage(String source) {
		
		Gson gson = new Gson();
		
		MessageDTO message = gson.fromJson(source, MessageDTO.class);
		
		return message;
	}
	
}
