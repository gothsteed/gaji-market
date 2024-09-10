package com.gaji.app.product.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private MemberService memberService;

    @GetMapping("productList")
    public String productList(Model model,
                              @RequestParam(defaultValue = "1") int pageNumber,
                              @RequestParam(defaultValue = "productseq") String sortType) {

        try {

            Page<ProductImage> pagingProductList = productService.getProductList(pageNumber, sortType);

            model.addAttribute("productList", pagingProductList.getContent());
            model.addAttribute("currentPage", (pagingProductList.getNumber() + 1)); // 현재 페이지 번호
            model.addAttribute("totalPages", pagingProductList.getTotalPages()); // 총 페이지 수

            // 페이지 번호 리스트 생성
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pagingProductList.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

            model.addAttribute("sortType", sortType);

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

        // 현재 로그인된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberUserDetail userDetail = (MemberUserDetail) authentication.getPrincipal();
        Long memberSeq  = userDetail.getMemberSeq();

        // 해당 상품의 정보 가져오기
        Product product = productService.getProductById(seq);
        Long productAuthorId = product.getFkMemberSeq();    // 상품 작성자 seq 가져오기

        // 현재 로그인한 사용자와 상품 작성자가 다를 경우 조회수 증가
        if (memberSeq != null && !memberSeq.equals(productAuthorId)) {
            productService.incrementViewCount(seq);
        }

        // 해당 상품의 모든 이미지 가져오기
        List<ProductImage> images = productService.getProductImgById(seq);

        model.addAttribute("memberSeq", memberSeq);
        model.addAttribute("product", product);
        model.addAttribute("images", images);

        return "product/productdetail";
    }


    @GetMapping("/product/search")
    public String search(@RequestParam String titleSearch, Model model) {
        model.addAttribute("titleSearch", titleSearch);
        model.addAttribute("title", titleSearch + " 검색 결과");
        return "product/productsearch";
    }





}
