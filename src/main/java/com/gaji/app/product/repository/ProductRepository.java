package com.gaji.app.product.repository;

import com.gaji.app.product.domain.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductImage, Long> {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.member.userId = :userid and p.completestatus='ONSALE'")
    int countOnSaleProductsByMemberSeq(@Param("userid") String userid);

    @Query("SELECT COUNT(p) FROM LikeProduct p WHERE p.member.userId = :userid")
    int countLikedProductByUserid(@Param("userid") String userid);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.member.userId = :userid and p.completestatus='ONSALE'")
    int countSoldProductsByMemberSeq(@Param("userid") String userid);
    // 상품당 상품이미지 1개씩 가져오기
    @Query(value = "SELECT t1.* " +
                   "FROM tbl_product_image t1 " +
                   "WHERE productimageseq = ( " +
                   "    SELECT MIN(t2.productimageseq) " +
                   "    FROM tbl_product_image t2 " +
                   "    WHERE t2.fkproductseq = t1.fkproductseq " +
                   ") ", nativeQuery = true)
    List<ProductImage> findMinProductImages();

}

