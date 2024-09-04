package com.gaji.app.member.controller;

import com.gaji.app.common.FileManager;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyPageController {

    private ProductService productService;

    @Autowired
    public MyPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/myPage")
    public String myPage(@RequestParam String userid, Model model) {
        model.addAttribute("userid", userid);
        return "member/mypage";
    }


    @GetMapping("/myPage/onSale")
    public String productList(Model model, @RequestParam String userid) {
        try {
            List<ProductImage> productList = productService.getUserOnSaleItem(userid);

            model.addAttribute("productList", productList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productpage";
    }
}
