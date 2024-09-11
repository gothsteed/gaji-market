package com.gaji.app.chatting.websockethandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ChattingMongoOperations {

	@Autowired
	private MongoOperations mongo;
	
	public void insertMessage(Mongo_message chattingMongoDto) throws Exception {
		
		try {
			mongo.save(chattingMongoDto, "chatting");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Mongo_message> listChatting() {
		
		List<Mongo_message> list = null;
		
		try {
			Query query = new Query();
			query.with(Sort.by(Sort.Direction.ASC, "_id"));
			list = mongo.find(query, Mongo_message.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
