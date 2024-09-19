package com.gaji.app.product.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tbl_category")
@SequenceGenerator(
        name = "CATEGORYSEQ",
        sequenceName = "CATEGORYSEQ",
        allocationSize = 1
)
public class Category {
	
	@Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "CATEGORYSEQ"
    )
    @Column(columnDefinition="NUMBER")
    private Long categoryseq;

    @Column(nullable = false, length = 100)
    private String name;

}
