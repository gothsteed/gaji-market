package com.gaji.app.product.service;

import com.gaji.app.product.domain.Product;
import com.gaji.app.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService_imple implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 리스트 가져오기
    @Override
    public List<Product> getProductList() throws Exception {

        List<Product> productList = null;

        productList = productRepository.findAll();

        return productList;
    }
}
