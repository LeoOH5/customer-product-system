package com.sparta.customerproductsystem.dto.product;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.role.ProductRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class PostProductResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String category;
    private ProductRole status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String message;
    private String warning;

    @Builder
    // 매개변수 순서 상관없이 사용 가능, 비어있는 건 null로 유연하게 처리
    private PostProductResponse(Long id, String name, int price,
                                int stockQuantity, String category, ProductRole status,
                                String imageUrl, LocalDateTime createdAt,
                                String message, String warning) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.status = status;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.message = message;
        this.warning = warning;
    }

    // 빌더에서 빠진 내용 추가용
    public static PostProductResponse from(Product p, String message, String warning) {
        return PostProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .stockQuantity(p.getStockQuantity())
                .category(p.getCategory())
                .status(p.inferStatus())
                .imageUrl(p.getImageUrl())
                .createdAt(p.getCreatedAt())
                .message(message)
                .warning(warning)
                .build();
    }
}


