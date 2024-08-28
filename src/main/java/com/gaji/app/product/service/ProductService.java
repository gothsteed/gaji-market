package com.gaji.app.product.service;

import com.gaji.app.product.domain.Product;

import java.util.List;

public interface ProductService {

    // 상품 리스트 가져오기
    List<Product> getProductList() throws Exception;
}
