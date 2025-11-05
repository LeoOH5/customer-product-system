package com.sparta.customerproductsystem.dto.reviewdto;

import com.sparta.customerproductsystem.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostReviewResponse {
    private final Long id;
    private final String productName;
    private final String description;
    private final String userName;
    private final double rating;
    private final LocalDateTime createdAt;

    public PostReviewResponse(Long id, String productName, String description, String userName, double rating, LocalDateTime createdAt) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.userName = userName;
        this.rating = rating;
        this.createdAt = createdAt;
    }


    public static PostReviewResponse from(Review r) {
        return new PostReviewResponse(
                r.getId(),
                r.getProduct().getName(),
                r.getDescription(),
                r.getUser().getName(),
                r.getRating(),
                r.getCreatedAt()
        );
    }
}

