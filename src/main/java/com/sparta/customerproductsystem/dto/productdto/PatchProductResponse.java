package com.sparta.customerproductsystem.dto.productdto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PatchProductResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String category;
    private String status;
    private LocalDateTime updatedAt;
    private String message;
    private String warning;

}
