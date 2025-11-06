package com.sparta.customerproductsystem.dto.productdto;

import com.sparta.customerproductsystem.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductDetailResponse {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String category;
    private String status;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean lowStock;
    private Long totalScales;

    /*
    리뷰 들어와야함
    private Long totalReviews;
    private double averageRating;
    */

    public static GetProductDetailResponse from(Product p, int lowStockThreshold) {
        return GetProductDetailResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stockQuantity(p.getStockQuantity())
                .category(p.getCategory())
                .status(p.getStockQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK")
                .imageUrl(p.getImageUrl())
                .createdDate(p.getCreatedAt())
                .updatedDate(p.getUpdatedAt())
                .lowStock(p.getStockQuantity() <= lowStockThreshold)
                .build();
    }
}
