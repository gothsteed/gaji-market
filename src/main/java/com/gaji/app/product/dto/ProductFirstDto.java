package com.gaji.app.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFirstDto {
    private Long productSeq;
    private String image;
    private String title;
    private Long likeCount;
    private Integer price;
}
