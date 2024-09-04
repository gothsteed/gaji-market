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


    // 상품번호에 대한 상품이미지 가져오기
    List<ProductImage> findByFkproductseq(Long seq);

    // 상품당 상품이미지 1개씩 가져오기
    @Query(value = "SELECT * " +
                   "FROM ( " +
                   "    SELECT T.*, ROW_NUMBER() over (order by T.fkproductseq desc) as RNUM " +
                   "    FROM ( " +
                   "        SELECT t1.* " +
                   "        FROM tbl_product_image t1 " +
                   "        WHERE productimageseq = ( " +
                   "            SELECT MIN(t2.productimageseq) " +
                   "            FROM tbl_product_image t2 " +
                   "            WHERE t2.fkproductseq = t1.fkproductseq " +
                   "        ) " +
                   "    ) T " +
                   ") " +
                "WHERE rnum BETWEEN :start and :end " +
                "ORDER BY RNUM ", nativeQuery = true)
    List<ProductImage> findMinProductImages(@Param("start") int start, @Param("end") int end);

    // 상품 전체 개수 구하기
    @Query(value = "SELECT COUNT(*) FROM tbl_product", nativeQuery = true)
    long countProduct();

    // 상품 좋아요 개수 구하기
    @Query(value = "SELECT count(*) as likecount " +
                   "FROM tbl_like_product " +
                   "GROUP BY fkproductseq " +
                   "having fkproductseq = :fkproductseq ", nativeQuery = true)
    Long countLikesByProductSeq(Long fkproductseq);
}

