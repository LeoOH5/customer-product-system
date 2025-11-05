package com.sparta.customerproductsystem.dto.reviewdto;

import com.sparta.customerproductsystem.domain.entity.Review;
import lombok.Getter;

@Getter
public class DeleteReviewByAdminResponse {
    private final Long reviewId;
    private final String message;
    private final String comment;

    public DeleteReviewByAdminResponse(Long reviewId, String message, String comment) {
        this.reviewId = reviewId;
        this.message = message;
        this.comment = comment;
    }

    public static DeleteReviewByAdminResponse from(Review review, String message, String comment) {
        return new DeleteReviewByAdminResponse(
                review.getId(),
                message,
                comment
        );
    }
}
