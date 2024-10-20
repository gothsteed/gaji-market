package com.gaji.app.mongo.dto;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

	private String senderSeq;
	private String senderNickname;
	private String senderType;
	private String content;

	// JSON 문자열을 MessageDTO 객체로 변환하는 static 메서드
	public static MessageDTO convertMessage(String source) {
		Gson gson = new Gson();
		return gson.fromJson(source, MessageDTO.class);
	}
	
}
