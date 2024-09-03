package com.gaji.app.product.controller;

import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("productList")
    public String productList(Model model,
                              @RequestParam(defaultValue = "1") int pageNumber) {

        try {

            Page<ProductImage> pagingProductList = productService.getProductList(pageNumber);

            model.addAttribute("productList", pagingProductList.getContent());
            model.addAttribute("currentPage", (pagingProductList.getNumber() + 1)); // 현재 페이지 번호
            model.addAttribute("totalPages", pagingProductList.getTotalPages()); // 총 페이지 수

            // 페이지 번호 리스트 생성
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pagingProductList.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

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
//            List<ProductImage> productList = productService.getProductList();
//
//            model.addAttribute("productList", productList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "product/productdetail";
    }






}
