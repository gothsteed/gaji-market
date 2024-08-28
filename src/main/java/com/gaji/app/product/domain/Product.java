package com.gaji.app.product.domain;

import com.gaji.app.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tbl_product")
@SequenceGenerator(
        name = "PRODUCTSEQ",
        sequenceName = "PRODUCTSEQ",
        allocationSize = 1
)
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "PRODUCTSEQ"
    )
    @Column(columnDefinition="NUMBER")
    private Long productseq;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkcategoryseq;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkkeywordseq;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, columnDefinition="NUMBER")
    private int price;

    @Column(nullable = false, length = 20)
    private String negostatus;

    @Column(nullable = false, length = 20)
    private String salestype;

    @Column(nullable = false, length = 20)
    private String completestatus;

    @Column(nullable = false, columnDefinition="DATE default sysdate")
    private LocalDateTime writedate;

    @Column(nullable = false, columnDefinition = "NUMBER default 0", insertable = false)
    private int reuploadcount;

    @Column(nullable = false, columnDefinition = "NUMBER default 0", insertable = false)
    private int viewcount;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, columnDefinition="NUMBER", updatable = false)
    private Long fkmemberseq;

    @Column(nullable = false, columnDefinition="DATE")
    private LocalDateTime startdatetime;

    @Column(nullable = false, columnDefinition="DATE")
    private LocalDateTime enddatetime;


    // 연관 관계 정의
    @ManyToOne
    @JoinColumn(name="fkmemberseq", referencedColumnName = "memberseq", insertable = false, updatable = false)
    private Member member;
    // insertable = false => insert 되는 것이 아니라 select 용이기 때문에
    // updatable = false => update 되는 것이 아니라 select 용이기 때문에

    @PrePersist // insert 전에 호출
    public void prePersist() {
        this.writedate = this.writedate == null ? LocalDateTime.now() : this.writedate; // 설정한 날짜가 null(default) 이면 현재 시간 설정, null이 아니면 설정되어있는 날짜를 넣어준다.
    }
}
