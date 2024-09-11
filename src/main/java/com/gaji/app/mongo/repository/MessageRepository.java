package com.gaji.app.mongo.repository;

import com.gaji.app.mongo.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {


}
