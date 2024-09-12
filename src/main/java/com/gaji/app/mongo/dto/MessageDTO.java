package com.gaji.app.mongo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

	private String senderId;
	private String senderNickname;
	private String senderType;
	private String content;
	
}
