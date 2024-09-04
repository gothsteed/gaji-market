package com.gaji.app.member.controller;

import com.gaji.app.common.FileManager;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String myPage(@RequestParam Long memberSeq, Model model) {
/*        if(memberSeq == null) {
            memberSeq = SecurityContextHolder.getContext().getAuthentication().getDetails()
        }*/
        model.addAttribute("memberSeq", memberSeq);
        return "member/mypage";
    }


    @GetMapping("/myPage/onSale")
    public String productList(Model model, @RequestParam Long memberSeq) {
        try {
            List<ProductImage> productList = productService.getUserOnSaleItem(memberSeq);

            model.addAttribute("productList", productList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productpage";
    }
}
