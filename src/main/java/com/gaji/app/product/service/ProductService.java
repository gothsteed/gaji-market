package com.gaji.app.product.service;

import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 리스트 가져오기
    public Page<ProductImage> getProductList(int pageNumber, String sortType) throws Exception {

        int pageSize = 16;
        int start = (pageNumber - 1) * pageSize + 1; // 시작 인덱스
        int end = start + pageSize - 1; // 종료 인덱스

        // 페이징된 결과를 가져옴
        List<ProductImage> pagingProductList = null;

        if(!"popular".equals(sortType)) {
            pagingProductList = productRepository.findMinProductImages(start, end, sortType);
        }

        for (ProductImage productImage : pagingProductList) {
            // 좋아요 수를 계산하여 설정
            Long likeCount = Optional.ofNullable(productRepository.countLikesByProductSeq(productImage.getFkproductseq()))
                            .orElse(0L);

            productImage.getProduct().setLikeCount(likeCount);
        }

        // 전체 데이터 수를 가져옴
        long totalCount = productRepository.countProduct();

        return new PageImpl<>(pagingProductList, PageRequest.of(pageNumber - 1, pageSize), totalCount);
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
