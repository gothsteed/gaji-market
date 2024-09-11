package com.gaji.app.keyword.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "keywordAlert")
public class KeywordAlert {
    private String keyword;
    private Long memberSeq;
}
