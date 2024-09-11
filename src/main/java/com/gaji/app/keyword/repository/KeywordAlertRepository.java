package com.gaji.app.keyword.repository;

import com.gaji.app.keyword.domain.KeywordAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeywordAlertRepository extends MongoRepository<KeywordAlert, String> {
}
