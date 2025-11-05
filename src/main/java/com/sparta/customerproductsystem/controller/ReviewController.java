package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.reviewdto.*;
import com.sparta.customerproductsystem.security.UserPrincipal;
import com.sparta.customerproductsystem.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 리뷰 삭제(사용자)
    @DeleteMapping("/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<DeleteReviewResponse> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        DeleteReviewResponse deleteReviewResponse = reviewService.DeleteReview(user, productId, reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteReviewResponse);
    }

    // 리뷰 리스트 조회 + 페이징 + 검색 기능 구현
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/reviews")
    public ResponseEntity<GetReviewListByAdminResponse> getAdminReviews(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        GetReviewListByAdminResponse response = reviewService.getAdminReviewResponse(keyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 리뷰 상세 조회 (관리자)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/reviews/{reviewId}")
    public ResponseEntity<GetReviewDetailByAdminResponse> getReviewDetailByAdmin(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        GetReviewDetailByAdminResponse response = reviewService.getReviewDetailByAdminResponse(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 리뷰 삭제 (관리자)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/reviews/{reviewId}")
    public ResponseEntity<DeleteReviewByAdminResponse> deleteReviewByAdmin(
            @PathVariable Long reviewId,
            @RequestBody(required = false) DeleteReviewByAdminRequest body
    ) {
        String comment = (body == null) ? null : body.getComment();
        DeleteReviewByAdminResponse response = reviewService.deleteReviewByAdmin(reviewId, comment); // (B) 사용
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
