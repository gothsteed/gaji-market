package com.gaji.app.product.repository;

import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.member.memberSeq = :memberSeq and p.completestatus='ONSALE'")
    int countOnSaleProductsByMemberSeq(@Param("memberSeq") Long memberSeq);

    @Query("SELECT COUNT(p) FROM LikeProduct p WHERE p.member.memberSeq = :memberSeq")
    int countLikedProductByUserid(@Param("memberSeq") Long memberSeq);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.member.memberSeq = :memberSeq and p.completestatus='ONSALE'")
    int countSoldProductsByMemberSeq(@Param("memberSeq") Long memberSeq);

    // 상품 전체 개수 구하기
    @Query(value = "SELECT COUNT(*) FROM tbl_product", nativeQuery = true)
    long countProduct();

    // 상품 좋아요 개수 구하기
    @Query(value = "SELECT count(*) as likecount " +
                   "FROM tbl_like_product " +
                   "GROUP BY fkproductseq " +
                   "having fkproductseq = :fkproductseq ", nativeQuery = true)
    Long countLikesByProductSeq(Long fkproductseq);

    List<Product> findAllByFkmemberseqAndCompletestatusIn(Long memberSeq, CompleteStatus... completeStatuses);
}

