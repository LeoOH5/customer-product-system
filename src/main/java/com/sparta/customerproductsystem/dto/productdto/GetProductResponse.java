package com.sparta.customerproductsystem.dto.productdto;

import com.sparta.customerproductsystem.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetProductResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String category;
    private String status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private boolean lowStock;

    public static GetProductResponse from(Product p, int lowStockThreshold) {
        return GetProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .stockQuantity(p.getStockQuantity())
                .category(p.getCategory())
                .status(p.getStockQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK")
                .imageUrl(p.getImageUrl())
                .createdAt(p.getCreatedAt())
                .lowStock(p.getStockQuantity() <= lowStockThreshold)
                .build();
    }

}
