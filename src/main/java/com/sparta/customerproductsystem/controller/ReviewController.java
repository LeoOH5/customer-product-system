package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.reviewdto.PostReviewRequest;
import com.sparta.customerproductsystem.dto.reviewdto.PostReviewResponse;
import com.sparta.customerproductsystem.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<PostReviewResponse> createReview(@PathVariable Long productId,
                                                           @Valid @RequestBody PostReviewRequest request) {
        PostReviewResponse response = reviewService.createReview(productId, request);
        return ResponseEntity.ok(response);
    }

}
