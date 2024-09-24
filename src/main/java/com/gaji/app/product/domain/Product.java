package com.gaji.app.product.domain;

import com.gaji.app.keyword.domain.Keyword;
import com.gaji.app.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
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

    @Column(nullable = false, columnDefinition="fkmemberseq")
    private Long fkMemberSeq;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkcategoryseq;


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
    @Enumerated(EnumType.STRING)
    private CompleteStatus completestatus;

    @Column(nullable = false, columnDefinition="DATE default sysdate")
    private LocalDateTime writedate;

    @Column(nullable = false, columnDefinition = "NUMBER default 0", insertable = false)
    private int reuploadcount;

    @Setter
    @Column(nullable = false, columnDefinition = "NUMBER default 0", insertable = false)
    private int viewcount;

    @Column(nullable = false, length = 200)
    private String address;
    
    @Column(nullable = true, length = 200)
    private String detailaddress;


    @Column(nullable = false, columnDefinition="DATE default sysdate")
    private LocalDateTime startdatetime;

    @Column(nullable = false, columnDefinition="DATE")
    private LocalDateTime enddatetime;

    @Column(nullable = false, columnDefinition="NUMBER default 0")
    private int likecount;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "KEYWORD")
    private Keyword keyword;



    public String getFirstImageName() {
        return productImages.get(0).getOriginalname();
    }


    // 연관 관계 정의
    @ManyToOne
    @JoinColumn(name="fkmemberseq", referencedColumnName = "memberseq", insertable = false, updatable = false)
    private Member member;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="fkcategoryseq", referencedColumnName = "categoryseq", insertable = false, updatable = false)
    private Category category;

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }


    public void incrementLikeCount() {
        this.likecount += 1;
    }
    
    public void decrementLikeCount() {
        this.likecount -= 1;
    }
    
    
    
    @PrePersist // insert 전에 호출
    public void prePersist() {
        this.writedate = this.writedate == null ? LocalDateTime.now() : this.writedate; // 설정한 날짜가 null(default) 이면 현재 시간 설정, null이 아니면 설정되어있는 날짜를 넣어준다.
    }
    

    public Product(Long fkcategoryseq, String title, String content, int price, String salestype, String address, CompleteStatus completestatus, String negostatus) {
        this.fkcategoryseq = fkcategoryseq;
        this.title = title;
        this.content = content;
        this.price = price;
        this.salestype = salestype;
        this.address = address;
        this.completestatus = CompleteStatus.FOR_SALE; // 또는 여기서 초기값 설정
        this.enddatetime = LocalDateTime.now().plusMonths(1); // 1개월 후 설정
    }

    public String getKeywordString() {
        return  keyword.getWord();
    }
}
