package com.gaji.app.product.repository;

import com.gaji.app.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductImage, Long> {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.member.userId = :userid and p.completestatus='ONSALE'")
    int countOnSaleProductsByMemberSeq(@Param("userid") String userid);

    @Query("SELECT COUNT(p) FROM LikeProduct p WHERE p.member.userId = :userid")
    int countLikedProductByUserid(@Param("userid") String userid);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.member.userId = :userid and p.completestatus='ONSALE'")
    int countSoldProductsByMemberSeq(@Param("userid") String userid);
}

