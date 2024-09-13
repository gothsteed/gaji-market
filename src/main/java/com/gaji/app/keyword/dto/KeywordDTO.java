package com.gaji.app.keyword.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordDTO {
    private String word;

    public KeywordDTO(String word) {
        this.word = word;
    }
}