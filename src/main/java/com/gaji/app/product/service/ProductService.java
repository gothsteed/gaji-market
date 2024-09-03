package com.gaji.app.product.service;

import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 리스트 가져오기
    public List<ProductImage> getProductList() throws Exception {

        List<ProductImage> productList = null;

        productList = productRepository.findMinProductImages();

        return productList;
    }

    // 해당 상품 정보 가져오기
    public Optional<ProductImage> getProductById(Long seq) {
        return productRepository.findById(seq);
    }

    // 해당 상품 이미지 가져오기
    public List<ProductImage> getProductImgById(Long seq) {
        return productRepository.findByFkproductseq(seq);
    }

}
