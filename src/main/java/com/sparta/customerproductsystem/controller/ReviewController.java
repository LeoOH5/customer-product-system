package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.reviewdto.*;
import com.sparta.customerproductsystem.security.UserPrincipal;
import com.sparta.customerproductsystem.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<PostReviewResponse> createReview(
            @PathVariable Long productId,
            @Valid @RequestBody PostReviewRequest request,
            @AuthenticationPrincipal UserPrincipal user
        ) {
        PostReviewResponse response = reviewService.createReview(user, productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<GetReviewsResponse>> getReviews(@PathVariable Long productId) {
        List<GetReviewsResponse> reviewsResponses = reviewService.getReviews(productId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewsResponses);
    }
}
