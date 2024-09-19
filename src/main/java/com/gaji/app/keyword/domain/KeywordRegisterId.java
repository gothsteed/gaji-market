package com.gaji.app.keyword.domain;

import java.io.Serializable;
import java.util.Objects;

public class KeywordRegisterId implements Serializable {
    
    private String keyword; // Keyword ID
    private Long member;  // Member ID

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeywordRegisterId that = (KeywordRegisterId) o;
        return Objects.equals(keyword, that.keyword) &&
               Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword, member);
    }
}

