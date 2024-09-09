package com.gaji.app.product.repository;

import com.gaji.app.product.domain.CompleteStatus;
import com.gaji.app.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.fkMemberSeq = :memberSeq and p.completestatus IN ('FOR_SALE', 'RESERVED')")
    int countOnSaleProductsByMemberSeq(@Param("memberSeq") Long memberSeq);

    @Query("SELECT COUNT(p) FROM LikeProduct p WHERE p.member.memberSeq = :memberSeq")
    int countLikedProductByUserid(@Param("memberSeq") Long memberSeq);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.fkMemberSeq = :memberSeq and p.completestatus IN ('SOLD')")
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





    default List<Product> searchProducts(
            String title,
            Integer minPrice,
            Integer maxPrice,
            String category,
            Long fkMemberSeq,
            List<String> completeStatusStrings,
            int minRow,
            int maxRow
    ) {
        Specification<Product> spec = createSpecification(title, minPrice, maxPrice, category, fkMemberSeq, completeStatusStrings);

        return findAll(spec)
                .stream()
                .skip(minRow)
                .limit(maxRow - minRow)
                .collect(Collectors.toList());
    }

    default long countSearchProducts(
            String title,
            Integer minPrice,
            Integer maxPrice,
            String category,
            Long fkMemberSeq,
            List<String> completeStatusStrings
    ) {
        Specification<Product> spec = createSpecification(title, minPrice, maxPrice, category, fkMemberSeq, completeStatusStrings);

        return count(spec);
    }

    private Specification<Product> createSpecification(
            String title,
            Integer minPrice,
            Integer maxPrice,
            String category,
            Long fkMemberSeq,
            List<String> completeStatusStrings
    ) {
        Specification<Product> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("title"), "%" + title + "%"));
        }
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category));
        }
        if (fkMemberSeq != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("fkMemberSeq"), fkMemberSeq));
        }
        if (completeStatusStrings != null && !completeStatusStrings.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("completestatus").in(completeStatusStrings));
        }

        return spec;
    }
}

