package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.dto.product.PostProductRequest;
import com.sparta.customerproductsystem.dto.product.PostProductResponse;
import com.sparta.customerproductsystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Product 등록
    @Transactional
    public PostProductResponse postProduct(PostProductRequest postProductRequest){

        if(productRepository.existsByName(postProductRequest.getName())){
            throw new IllegalStateException("중복된 상품입니다.");
        }

        Product product = Product.builder()
                .name(postProductRequest.getName())
                .description(postProductRequest.getDescription())
                .price(postProductRequest.getPrice())
                .stockQuantity(postProductRequest.getStockQuantity())
                .category(postProductRequest.getCategory())
                .imageUrl(postProductRequest.getImageUrl())
                .build();

        Product savedProduct = productRepository.save(product);

        return PostProductResponse.from(savedProduct,"상품이 성공적으로 등록되었습니다.", null);
    }
}
