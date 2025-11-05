package com.sparta.customerproductsystem.dto.reviewdto;

import com.sparta.customerproductsystem.domain.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneReviewResponse {
    private final Long reviewId;
    private final String productName;
    private final String description;
    private final String userName;
    private final double rating;
    private final LocalDateTime createdAt;


    public GetOneReviewResponse(Long reviewId, String productName, String description, String userName, double rating, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.productName = productName;
        this.description = description;
        this.userName = userName;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public static GetOneReviewResponse from(Review review) {
        return new GetOneReviewResponse(
                review.getId(),
                review.getProduct().getName(),
                review.getDescription(),
                review.getUser().getName(),
                review.getRating(),
                review.getCreatedAt()
        );
    }
}
