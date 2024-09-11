package com.gaji.app.chatting.websockethandler;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mongo_message {

	private String _id;
	
	private String message;
	private String type;
	private String to;
	
	private String userid;
	private String name;
	private String currentTime;
	
	private Date created;
}
