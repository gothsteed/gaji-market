package com.gaji.app.keyword.domain;

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
public class KeywordRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_KEYWORD_REGISTER_id_gen")
    @SequenceGenerator(name = "TBL_KEYWORD_REGISTER_id_gen", sequenceName = "KEYWORDREGISTERSEQ", allocationSize = 1)
    @Column(name = "REGISTERSEQ", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "FKKEYWORDSEQ")
    private Keyword fkkeywordseq;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "FKMEMBERSEQ")
    private Member fkmemberseq;

}