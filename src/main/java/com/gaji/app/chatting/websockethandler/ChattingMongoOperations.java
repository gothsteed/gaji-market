package com.gaji.app.chatting.websockethandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gaji.app.mongo.entity.Message;

@Service
public class ChattingMongoOperations {

	@Autowired
	private MongoOperations mongo;
	
	public void insertMessage(Message chattingMongoDto) throws Exception {
		
		try {
			mongo.save(chattingMongoDto, "chatting");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Message> listChatting() {
		
		List<Message> list = null;
		
		try {
			Query query = new Query();
			query.with(Sort.by(Sort.Direction.ASC, "_id"));
			list = mongo.find(query, Message.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
