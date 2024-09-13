package com.gaji.app.keyword.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gaji.app.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(KeywordRegisterId.class)
@Table(name = "TBL_KEYWORD_REGISTER")
public class KeywordRegister {
	
	@Id
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "keyword")
    private Keyword keyword;

	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "FKMEMBERSEQ")
    private Member member;

    // 생성자 추가
    public KeywordRegister(Keyword keyword, Member member) {
        this.keyword = keyword;
        this.member = member;
    }
    
}