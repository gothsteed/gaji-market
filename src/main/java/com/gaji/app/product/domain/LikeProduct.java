package com.gaji.app.product.domain;

import com.gaji.app.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tbl_like_product")
@SequenceGenerator(
        name = "LIKESEQ",
        sequenceName = "LIKESEQ",
        allocationSize = 1
)

public class LikeProduct {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "LIKESEQ"
    )
    @Column(columnDefinition="NUMBER")
    private Long likeseq;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkproductseq;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkmemberseq;
    
    // 연관 관계 정의
    @ManyToOne
    @JoinColumn(name="fkmemberseq", referencedColumnName = "memberseq", insertable = false, updatable = false)
    private Member member;

    // 연관 관계 정의
    @ManyToOne
    @JoinColumn(name="fkproductseq", referencedColumnName = "productseq", insertable = false, updatable = false)
    private Product product;
    
    public LikeProduct(Long fkproductseq, Long fkmemberseq) {
        this.fkproductseq = fkproductseq;
        this.fkmemberseq = fkmemberseq;
    }

}
