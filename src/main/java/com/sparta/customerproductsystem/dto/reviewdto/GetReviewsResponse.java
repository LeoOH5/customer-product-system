package com.sparta.customerproductsystem.dto.reviewdto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetReviewsResponse {
    private final Long productId;
    private final String productName;
    private final String description;
    private final String userName;
    private final double rating;
    private final LocalDateTime createdAt;

    public GetReviewsResponse(Long productId, String productName, String description, String userName, double rating, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.userName = userName;
        this.rating = rating;
        this.createdAt = createdAt;
    }
}
