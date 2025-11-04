package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.dto.product.GetProductPageResponse;
import com.sparta.customerproductsystem.dto.product.GetProductResponse;
import com.sparta.customerproductsystem.dto.product.PostProductRequest;
import com.sparta.customerproductsystem.dto.product.PostProductResponse;
import com.sparta.customerproductsystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${LOWSTOCK_THRESHOLD}")
    private int lowStockThreshold;


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

    // Product 조회
    @Transactional(readOnly = true)
    public GetProductPageResponse getProduct(int page, int size) {

        page = Math.max(page, 0);
        // 1~100 사이로 제한
        size = Math.min(Math.max(size, 1), 100);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));


        Page<Product> pageData = productRepository.findAll(pageable);

        List<GetProductResponse> content = new ArrayList<>(pageData.getNumberOfElements());
        for (Product p : pageData.getContent()) {
            content.add(GetProductResponse.from(p, lowStockThreshold));
        }

        return GetProductPageResponse.of(content, pageData);
    }


}
