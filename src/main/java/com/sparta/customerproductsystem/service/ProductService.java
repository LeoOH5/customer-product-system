package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.dto.productdto.*;
import com.sparta.customerproductsystem.exception.BusinessException;
import com.sparta.customerproductsystem.exception.ErrorCode;
import com.sparta.customerproductsystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
            throw BusinessException.of(ErrorCode.INVALID_PRODUCT_NAME);
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

    // Product 상세 조회
    @Transactional(readOnly = true)
    public GetProductDetailResponse getProductDetail(Long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(ErrorCode.INVALID_PRODUCT_ID));
        return GetProductDetailResponse.from(product, lowStockThreshold);

    }

    // Product 수정
    @Transactional
    public PatchProductResponse patchProduct(Long id, PatchProductRequest patchProductRequest) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(ErrorCode.INVALID_PRODUCT_ID));

        if (patchProductRequest.getName() != null) {
            product.setName(patchProductRequest.getName());
        }
        if (patchProductRequest.getPrice() != null) {
            product.setPrice(patchProductRequest.getPrice());
        }
        if (patchProductRequest.getStockQuantity() != null) {
            if(patchProductRequest.getStockQuantity() < 0){
                BusinessException.of(ErrorCode.INVALID_PRODUCT_UPDATE);
            }
            product.setStockQuantity(patchProductRequest.getStockQuantity());
        }

        String status = product.getStockQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK";

        return PatchProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .status(status)
                .updatedAt(LocalDateTime.now())
                .message("상품 정보가 수정되었습니다.")
                .warning(null)
                .build();

    }

    // Product 검색
    @Transactional(readOnly = true)
    public List<GetProductSearchResponse> searchProducts(String keyword) {
        List<Product> products = productRepository
                .findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword);

        return products.stream()
                .map(GetProductSearchResponse::from)
                .toList();
    }

}
