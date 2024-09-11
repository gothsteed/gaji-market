package com.gaji.app.reservation.domain;

import com.gaji.app.member.domain.Member;
import com.gaji.app.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tbl_reservation")
@SequenceGenerator(
        name = "RESERVATIONSEQ",
        sequenceName = "RESERVATIONSEQ",
        allocationSize = 1
)
public class Reservation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "RESERVATIONSEQ"
    )
    @Column(columnDefinition="NUMBER")
    private Long reservationseq;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkproductseq;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkmemberseq;

    // 연관 관계 정의
    @ManyToOne
    @JoinColumn(name="fkproductseq", referencedColumnName = "productseq", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="fkmemberseq", referencedColumnName = "memberseq", insertable = false, updatable = false)
    private Member member;

}
