package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.product.GetProductListResponse;
import com.sparta.customerproductsystem.dto.product.PostProductRequest;
import com.sparta.customerproductsystem.dto.product.PostProductResponse;
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
    public ResponseEntity<GetProductListResponse> list() {
        return ResponseEntity.ok(productService.getProduct());
    }
}
