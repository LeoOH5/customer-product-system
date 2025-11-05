package com.sparta.customerproductsystem.dto.reviewdto;

import com.sparta.customerproductsystem.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostReviewResponse {
    private Long id;
    private Long productId;
    private Long userId;
    private String username;
    private String productName;
    private double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostReviewResponse from(Review r) {
        return new PostReviewResponse(
                r.getId(),
                r.getProduct().getId(),
                r.getUser().getId(),
                r.getUsername(),
                r.getProductName(),
                r.getRating(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }
}

