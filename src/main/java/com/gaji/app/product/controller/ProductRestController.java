package com.gaji.app.product.controller;


import com.gaji.app.product.dto.ProductFirstDto;
import com.gaji.app.product.dto.ProductListDto;
import com.gaji.app.product.dto.ProductSearchParamDto;
import com.gaji.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<ProductListDto> searchProducts(ProductSearchParamDto paramDto) {
        ProductListDto products = productService.searchProduct(paramDto);

        return ResponseEntity.ok(products);
    }
}
