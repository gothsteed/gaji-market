package com.gaji.app.product.controller;

import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("productList")
    public String productList(Model model) {

        try {
            List<ProductImage> productList = productService.getProductList();

            model.addAttribute("productList", productList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productpage";
    }

    @GetMapping("productRegister")
    public String productRegister() {
        return "product/productregister";
    }


    @GetMapping("productDetail")
    public String productDetail(@RequestParam Long seq, Model model) {

        // 해당 상품의 정보 가져오기
        Product product = productService.getProductById(seq)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product seq: " + seq)).getProduct();

        // 해당 상품의 모든 이미지 가져오기
        List<ProductImage> images = productService.getProductImgById(seq);

        model.addAttribute("product", product);
        model.addAttribute("images", images);

        return "product/productdetail";
    }





}
