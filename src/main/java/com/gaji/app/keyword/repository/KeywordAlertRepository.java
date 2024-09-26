package com.gaji.app.keyword.repository;

import com.gaji.app.keyword.domain.KeywordAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface KeywordAlertRepository extends MongoRepository<KeywordAlert, String> {

    List<KeywordAlert> findByMemberSeq(long memberSeq);
}
