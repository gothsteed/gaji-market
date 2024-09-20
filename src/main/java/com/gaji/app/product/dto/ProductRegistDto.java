package com.gaji.app.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRegistDto {
    private Long productSeq;
    private String image;
    private String title;
    private Long likeCount;
    private Integer price;

}
