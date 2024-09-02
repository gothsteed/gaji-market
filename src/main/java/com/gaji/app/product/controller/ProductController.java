package com.gaji.app.product.controller;

import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
    public String productDetail(Model model) {
        try {
            List<ProductImage> productList = productService.getProductList();

            model.addAttribute("productList", productList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "product/productdetail";
    }






}
