package com.gaji.app.product.service;

import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 리스트 가져오기
    public Page<ProductImage> getProductList(int pageNumber) throws Exception {

        int pageSize = 16;
        int start = (pageNumber - 1) * pageSize + 1; // 시작 인덱스
        int end = start + pageSize - 1; // 종료 인덱스

        // 페이징된 결과를 가져옴
        List<ProductImage> pagingProductList = productRepository.findMinProductImages(start, end);

        // 디버깅 로그 추가
        System.out.println("Paging Product List Size: " + pagingProductList.size());

        for (ProductImage productImage : pagingProductList) {
            // 좋아요 수를 계산하여 설정
            Long likeCount = Optional.ofNullable(productRepository.countLikesByProductSeq(productImage.getFkproductseq()))
                            .orElse(0L);

            productImage.getProduct().setLikeCount(likeCount);
            System.out.println("!!! 좋아요 수 : " + productImage.getProduct().getLikeCount());
        }

        // 전체 데이터 수를 가져옴
        long totalCount = productRepository.countProduct();

        return new PageImpl<>(pagingProductList, PageRequest.of(pageNumber - 1, pageSize), totalCount);
    }
}
