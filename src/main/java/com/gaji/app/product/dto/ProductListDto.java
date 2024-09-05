package com.gaji.app.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDto {
    private String image;
    private String title;
    private String likeCount;
    private Integer price;
}
