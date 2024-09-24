package com.gaji.app.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gaji.app.product.domain.ProductImage;

public interface CategoryRepository extends JpaRepository<ProductImage, Long> {
	
	 @Query(value = "SELECT categoryseq, name FROM tbl_category", nativeQuery = true) // 카테고리 이름 조회 쿼리
	 List<Object[]> getAllCategoryInfo(); // // Category 엔티티 리스트 반환

}
