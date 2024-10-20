package com.gaji.app.keyword.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "keywordAlert") 
public class KeywordAlert {
	
    @Id
	private String _id;
	
	private String word;
	private Long memberSeq;
	private Long productSeq;
	private LocalDateTime currentTime;
	
	public KeywordAlert(String word, Long memberSeq, Long productSeq, LocalDateTime currentTime) {
		this.word = word;
		this.memberSeq = memberSeq;
		this.productSeq = productSeq;
		this.currentTime = currentTime;
	}


}
