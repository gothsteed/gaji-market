package com.gaji.app.keyword.domain;

import com.gaji.app.keyword.repository.KeywordAlertRepository;
import com.gaji.app.keyword.repository.KeywordRegisterRepository;
import com.gaji.app.keyword.service.AlertObserver;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_KEYWORD")
public class Keyword {
    @Id
    @Nationalized
    @Column(name = "WORD", nullable = false, length = 100)
    private String word;

    public Keyword(String word) {
        this.word = word;
    }

    //todo: n + 1 문제 해결
    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KeywordRegister> keywordRegisters;


}