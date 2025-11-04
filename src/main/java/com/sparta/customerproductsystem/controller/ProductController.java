package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.productdto.*;
import com.sparta.customerproductsystem.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Product 등록
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PostProductResponse> create(@Valid @RequestBody PostProductRequest postProductRequest){
        PostProductResponse postProductResponse = productService.postProduct(postProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postProductResponse);
    }

    // Product 조회
    @GetMapping
    public ResponseEntity<GetProductPageResponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(productService.getProduct(page, size));
    }

    // Product 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetProductDetailResponse> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductDetail(id));
    }

    // Product 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PatchProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody PatchProductRequest request
    ) {
        return ResponseEntity.ok(productService.patchProduct(id, request));
    }
}
