package com.sparta.customerproductsystem.dto.reviewdto;

import com.sparta.customerproductsystem.domain.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetReviewDetailByAdminResponse {
    private final Long reviewId;
    private final String productName;
    private final String description;
    private final String userName;
    private final double rating;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public GetReviewDetailByAdminResponse(Long reviewId, String productName, String description, String userName, double rating, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reviewId = reviewId;
        this.productName = productName;
        this.description = description;
        this.userName = userName;
        this.rating = rating;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static GetReviewDetailByAdminResponse from(Review review){
        return new GetReviewDetailByAdminResponse(
                review.getId(),
                review.getProduct().getName(),
                review.getDescription(),
                review.getUser().getName(),
                review.getRating(),
                review.getUser().getEmail(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
