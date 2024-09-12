package com.gaji.app.keyword.domain;

import com.gaji.app.keyword.service.AlertObserver;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TBL_KEYWORD")
public class Keyword implements AlertObserver {
    @Id
    @Nationalized
    @Column(name = "WORD", nullable = false, length = 100)
    private String word;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KeywordRegister> keywordRegisters;

    @Override
    public void alert(String message) {

    }
}