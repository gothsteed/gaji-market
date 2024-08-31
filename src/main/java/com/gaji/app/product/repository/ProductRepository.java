package com.gaji.app.product.repository;

import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductImage, Long> {

}
