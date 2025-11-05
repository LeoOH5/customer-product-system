package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.PatchUserUpdateRequest;
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

    // 리뷰 등록
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<PostReviewResponse> createReview(
            @PathVariable Long productId,
            @Valid @RequestBody PostReviewRequest request,
            @AuthenticationPrincipal UserPrincipal user
        ) {
        PostReviewResponse response = reviewService.createReview(user, productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 리뷰 다건 조회
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<GetReviewsResponse>> getReviews(@PathVariable Long productId) {
        List<GetReviewsResponse> reviewsResponses = reviewService.getReviews(productId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewsResponses);
    }

    // 리뷰 단건 조회
    @GetMapping("/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<GetOneReviewResponse> getOneReview(@PathVariable Long reviewId) {
        GetOneReviewResponse getOneReviewResponse = reviewService.getOneReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(getOneReviewResponse);
    }

    // 리뷰 수정
    @PatchMapping("/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<PatchReviewResponse> patchReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @Valid @RequestBody PatchReviewRequest reviewRequest,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        PatchReviewResponse response = reviewService.patchReview(user, productId, reviewId, reviewRequest);
        return ResponseEntity.ok(response);
    }
}
