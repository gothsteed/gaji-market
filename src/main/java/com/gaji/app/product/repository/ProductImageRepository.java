package com.gaji.app.product.repository;

import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // 상품번호에 대한 상품이미지 가져오기
    List<ProductImage> findByFkproductseq(Long seq);

    // 상품당 상품이미지 1개씩 가져오기
    @Query(value = "SELECT * " +
            "FROM ( " +
            "    SELECT T.*, " +
            "           ROW_NUMBER() over (order by " +
            "            CASE :sortType " +
            "                WHEN 'productseq' THEN T.productseq " +
            "                WHEN 'viewcount' THEN T.viewcount " +
            "                ELSE T.productseq " +
            "            END desc) as RNUM " +
            "    FROM ( " +
            "        SELECT t1.*, P.* " +
            "        FROM tbl_product_image t1 JOIN tbl_product P " +
            "        ON t1.fkproductseq = P.productseq " +
            "        WHERE productimageseq = ( " +
            "            SELECT MIN(t2.productimageseq) " +
            "            FROM tbl_product_image t2 " +
            "            WHERE t2.fkproductseq = t1.fkproductseq " +
            "        ) " +
            "    ) T " +
            ") " +
            "WHERE rnum BETWEEN :start and :end " +
            "ORDER BY RNUM ", nativeQuery = true)

    List<ProductImage> findMinProductImages(@Param("start") int start, @Param("end") int end, @Param("sortType") String sortType);
    

}
