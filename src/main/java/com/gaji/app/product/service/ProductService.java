package com.gaji.app.product.service;

import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.dto.ProductFirstDto;
import com.gaji.app.product.dto.ProductListDto;
import com.gaji.app.product.dto.ProductSearchParamDto;
import com.gaji.app.product.repository.ProductImageRepository;
import com.gaji.app.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    // 상품 리스트 가져오기
    public Page<ProductImage> getProductList(int pageNumber, String sortType) throws Exception {

        int pageSize = 16;
        int start = (pageNumber - 1) * pageSize + 1; // 시작 인덱스
        int end = start + pageSize - 1; // 종료 인덱스

        // 페이징된 결과를 가져옴
        List<ProductImage> pagingProductList = productImageRepository.findMinProductImages(start, end, sortType);

        // 전체 데이터 수를 가져옴
        long totalCount = productRepository.countProduct();

        return new PageImpl<>(pagingProductList, PageRequest.of(pageNumber - 1, pageSize), totalCount);
    }



    // 해당 상품 정보 가져오기
    public Product getProductById(Long seq) {
        return productRepository.findById(seq)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product seq: " + seq));
    }

    // 상품 조회수 증가 메서드
    public void incrementViewCount(Long seq) {
        Product product = productRepository.findById(seq)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setViewcount(product.getViewcount() + 1); // 조회수 증가
        productRepository.save(product); // 변경사항 저장
    }

    // 해당 상품 이미지 가져오기
    public List<ProductImage> getProductImgById(Long seq) {
        return productImageRepository.findByFkproductseq(seq);
    }

    public ProductListDto searchProduct(ProductSearchParamDto paramDto) {
        int page = paramDto.getPage();
        int size = paramDto.getSize();
        int minRow = (page - 1) * size;
        int maxRow = (page) * size;
        List<String> completeStatusStrings = paramDto.getCompleteStatus() != null ?
                paramDto.getCompleteStatus().stream().map(Enum::name).collect(Collectors.toList()) : null;

        List<Product> productList = productRepository.searchProducts(
                paramDto.getTitle(),
                paramDto.getMinPrice(),
                paramDto.getMaxPrice(),
                paramDto.getCategory(),
                paramDto.getFkMemberSeq(),
                completeStatusStrings,
                paramDto.getSortType(),
                minRow,
                maxRow
        );

        long totalPage = productRepository.countSearchProducts(
                paramDto.getTitle(),
                paramDto.getMinPrice(),
                paramDto.getMaxPrice(),
                paramDto.getCategory(),
                paramDto.getFkMemberSeq(),
                completeStatusStrings,
                paramDto.getSortType()
        ) / size + 1;

        List<ProductFirstDto> dtos = new ArrayList<>();
        for(Product product : productList) {
            ProductFirstDto dto = new ProductFirstDto();
            dto.setSeq(product.getProductseq());
            dto.setImage(product.getFirstImageName());
            dto.setTitle(product.getTitle());
            dto.setPrice(product.getPrice());
            dtos.add(dto);
        }

        ProductListDto productListDto = new ProductListDto();
        productListDto.setProductList(dtos);
        productListDto.setSize(size);
        productListDto.setTotalPage(totalPage);
        productListDto.setPage(page);


        return productListDto;
    }
}
