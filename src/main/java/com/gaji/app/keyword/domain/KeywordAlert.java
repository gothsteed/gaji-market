package com.gaji.app.keyword.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "keywordAlert")
public class KeywordAlert {
    private Long productSeq;
    private String keyword;
    private Long memberSeq;


}
