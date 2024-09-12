package com.gaji.app.keyword.domain;

import com.gaji.app.keyword.service.AlertObserver;
import com.gaji.app.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "TBL_KEYWORD_REGISTER")
@SequenceGenerator(
        name = "KEYWORDREGISTERSEQ",
        sequenceName = "KEYWORDREGISTERSEQ",
        allocationSize = 1
)
public class KeywordRegister {
    @Id
    @SequenceGenerator(name = "KEYWORDREGISTERSEQ", sequenceName = "KEYWORDREGISTERSEQ", allocationSize = 1)
    @Column(name = "REGISTERSEQ", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "keyword")
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "FKMEMBERSEQ")
    private Member member;




}