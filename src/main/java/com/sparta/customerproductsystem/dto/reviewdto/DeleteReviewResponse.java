package com.sparta.customerproductsystem.dto.reviewdto;

import com.sparta.customerproductsystem.domain.entity.Review;
import lombok.Getter;

@Getter
public class DeleteReviewResponse {
    private final Long reviewId;
    private final String productName;
    private final String description;
    private final String message;

    public DeleteReviewResponse(Long reviewId, String productName, String description, String message) {
        this.reviewId = reviewId;
        this.productName = productName;
        this.description = description;
        this.message = message;
    }

    public static DeleteReviewResponse from(Review review, String message) {
        return  new DeleteReviewResponse(
                review.getId(),
                review.getProduct().getName(),
                review.getDescription(),
                message
        );
    }
}
