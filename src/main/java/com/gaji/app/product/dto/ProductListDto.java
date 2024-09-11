package com.gaji.app.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListDto {
    List<ProductFirstDto> productList;
    Integer page;
    Integer size;
    Long totalPage;
}
