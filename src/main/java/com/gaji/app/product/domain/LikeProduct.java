package com.gaji.app.product.domain;

import com.gaji.app.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "TBL_LIKE_PRODUCT")
public class LikeProduct {
    @Id
    @Column(name = "LIKESEQ", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "FKMEMBERSEQ", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "FKPRODUCTSEQ", nullable = false)
    private Product product;

}