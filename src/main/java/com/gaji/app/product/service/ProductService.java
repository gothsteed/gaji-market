package com.gaji.app.product.service;

import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;

import java.util.List;

public interface ProductService {

    // 상품 리스트 가져오기
    List<ProductImage> getProductList() throws Exception;
}
