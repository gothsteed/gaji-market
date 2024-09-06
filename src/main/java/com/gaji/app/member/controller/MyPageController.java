package com.gaji.app.member.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.common.FileManager;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public String myPage(@RequestParam(required = false) Long memberSeq, Model model) {

        if (memberSeq == null) {
            // 현재 로그인된 사용자 정보를 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MemberUserDetail userDetail = (MemberUserDetail) authentication.getPrincipal();
            memberSeq = userDetail.getMemberSeq();
        }

        model.addAttribute("memberSeq", memberSeq);
        return "member/mypage";
    }


    @GetMapping("/myPage/onSale")
    public String productList(Model model, @RequestParam Long memberSeq) {
        try {

            List<Product> productList = productService.getUserOnSaleItem(memberSeq);

            model.addAttribute("productList", productList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productpage";
    }
}
