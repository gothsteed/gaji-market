package com.gaji.app.mongo.repository;

import com.gaji.app.mongo.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {


    List<Message> findAllByRoomId(String roomId);
}
