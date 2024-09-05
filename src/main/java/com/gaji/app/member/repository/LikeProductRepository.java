package com.gaji.app.member.repository;

import com.gaji.app.member.domain.Member;
import com.gaji.app.product.domain.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeProductRepository extends JpaRepository<LikeProduct, Long> {
    
    // 사용자가 좋아요를 표시한 상품 목록을 조회하는 메서드
    List<LikeProduct> findByMember(Member member);
}
