package com.gaji.app.chatting.websockethandler;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

	private String message;
	private String type;
	private String to;
	
	public static Message convertMessage(String source) {
		
		Gson gson = new Gson();
		
		Message message = gson.fromJson(source, Message.class);
		
		return message;
	}
	
}
