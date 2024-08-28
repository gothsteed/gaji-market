package com.gaji.app.product.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class productController {

    @GetMapping("productList")
    public String productList() {
        return "product/productpage";
    }

    @GetMapping("productRegister")
    public String productRegister() {
        return "product/productregister";
    }
}
