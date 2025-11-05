package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.productdto.*;
import com.sparta.customerproductsystem.exception.CommonResponse;
import com.sparta.customerproductsystem.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommonResponse<PostProductResponse>> create(
            @Valid @RequestBody PostProductRequest postProductRequest) {

        PostProductResponse result = productService.postProduct(postProductRequest);
        CommonResponse<PostProductResponse> body = CommonResponse.<PostProductResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


    // Product 조회
    @GetMapping
    public ResponseEntity<CommonResponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        GetProductPageResponse result = productService.getProduct(page, size);

        CommonResponse<GetProductPageResponse> body = CommonResponse.<GetProductPageResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.ok(body);
    }

    // Product 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getProduct(@PathVariable Long id){

        GetProductDetailResponse result = productService.getProductDetail(id);

        CommonResponse<GetProductDetailResponse> body = CommonResponse.<GetProductDetailResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.ok(body);
    }

    // Product 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody PatchProductRequest request
    ) {
        PatchProductResponse result = productService.patchProduct(id, request);

        CommonResponse<PatchProductResponse> body = CommonResponse.<PatchProductResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.ok(body);
    }

    // Product 검색
    @GetMapping("/keyword")
    public ResponseEntity<CommonResponse<List<GetProductSearchResponse>>> searchProducts(@RequestParam String q) {
        List<GetProductSearchResponse> result = productService.searchProducts(q);

        CommonResponse<List<GetProductSearchResponse>> body = CommonResponse.<List<GetProductSearchResponse>>builder()
                .data(result)
                .build();

        return ResponseEntity.ok(body);
    }

}
