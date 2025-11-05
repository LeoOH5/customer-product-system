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
public class GetProductSearchResponse {

    private Long id;
    private String name;
    private int quantity;
    private String category;
    private int price;
    private String status;
    private LocalDateTime createdAt;

    public static GetProductSearchResponse from(Product p) {
        return GetProductSearchResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .quantity(p.getStockQuantity())
                .category(p.getCategory())
                .price(p.getPrice())
                .status(p.getStockQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK")
                .createdAt(p.getCreatedAt())
                .build();
    }

}
