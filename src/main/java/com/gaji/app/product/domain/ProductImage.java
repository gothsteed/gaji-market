package com.gaji.app.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_product_image")
@SequenceGenerator(
        name = "PRODUCTIMAGESEQ",
        sequenceName = "PRODUCTIMAGESEQ",
        allocationSize = 1
)
public class ProductImage {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
            , generator = "PRODUCTIMAGESEQ"
    )
    @Column(columnDefinition="NUMBER")
    private Long productimageseq;

    @Column(nullable = false, length = 200)
    private String originalname;

    @Column(nullable = false, length = 200)
    private String filename;

    @Column(nullable = false, columnDefinition="NUMBER")
    private Long fkproductseq;

    
    // 연관 관계 정의
    @ManyToOne
    @JoinColumn(name="fkproductseq", referencedColumnName = "productseq", insertable = false, updatable = false)
    private Product product;
    // insertable = false => insert 되는 것이 아니라 select 용이기 때문에
    // updatable = false => update 되는 것이 아니라 select 용이기 때문에

}
