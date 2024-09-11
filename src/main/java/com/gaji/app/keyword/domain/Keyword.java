package com.gaji.app.keyword.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "TBL_KEYWORD")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_KEYWORD_id_gen")
    @SequenceGenerator(name = "TBL_KEYWORD_id_gen", sequenceName = "KEYWORDSEQ", allocationSize = 1)
    @Column(name = "KEYWORDSEQ", nullable = false)
    private Long id;

    @Nationalized
    @Column(name = "WORD", nullable = false, length = 100)
    private String word;

}