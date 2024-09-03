package com.gaji.app.review.domain;

import com.gaji.app.member.domain.Member;
import com.gaji.app.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "TBL_REVIEW")
public class Review {
    @Id
    @Column(name = "REVIEWSEQ", nullable = false)
    private Long id;

    @Nationalized
    @Column(name = "CONTENT", nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "FKPRODUCTSEQ", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "FKMEMBERSEQ", nullable = false)
    private Member member;

}