package com.gaji.app.mongo.dto;

import java.util.List;

import com.gaji.app.mongo.entity.ChatRoom;
import com.gaji.app.mongo.entity.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomWithMessages {

	private ChatRoom chatRoom;
    private List<Message> messages;

    public ChatRoomWithMessages(ChatRoom chatRoom, List<Message> messages) {
        this.chatRoom = chatRoom;
        this.messages = messages;
    }
	
}
