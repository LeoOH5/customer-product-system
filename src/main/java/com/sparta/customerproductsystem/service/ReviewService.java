package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Review;
import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.reviewdto.*;
import com.sparta.customerproductsystem.exception.BusinessException;
import com.sparta.customerproductsystem.exception.ErrorCode;
import com.sparta.customerproductsystem.repository.ProductRepository;
import com.sparta.customerproductsystem.repository.ReviewRepository;
import com.sparta.customerproductsystem.repository.UserRepository;
import com.sparta.customerproductsystem.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 등록
    @Transactional
    public PostReviewResponse createReview(UserPrincipal user, Long productId, PostReviewRequest req) {
        // 현재 로그인 사용자 조회
        Users users = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (reviewRepository.existsByUserAndProduct(users, product)) {
            throw new BusinessException(ErrorCode.REVIEW_DUPLICATED_REVIEW);
        }

        // 리뷰 생성 + 리뷰 내용, 평점 담기
        Review review = Review.create(users, product, req.getRating());
        review.setDescription(req.getDescription());

        // 리뷰 저장
        Review saved = reviewRepository.save(review);

        // 응답 변환
        return PostReviewResponse.from(saved);
    }

    // 다건 리뷰 조회
    @Transactional(readOnly = true)
    public List<GetReviewsResponse> getReviews(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        List<Review> reviews = reviewRepository.findByProductId(productId);

        return reviews.stream()
                .map(r -> new GetReviewsResponse(
                        r.getProduct().getId(),
                        r.getProduct().getName(),
                        r.getDescription(),
                        r.getUser().getName(),
                        r.getRating(),
                        r.getCreatedAt()
                ))
                .toList();
    }

    // 단건 리뷰 조회 기능 구현
    @Transactional(readOnly = true)
    public GetOneReviewResponse getOneReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        return GetOneReviewResponse.from(review);
    }

    // 리뷰 수정 기능 구현
    @Transactional
    public PatchReviewResponse patchReview(
            UserPrincipal principal, Long productId, Long reviewId, PatchReviewRequest req) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다. id=" + reviewId));

        // 작성자 본인 여부 검증
        boolean isOwner = review.getUser().getId().equals(principal.getId());
        if (!isOwner) {
            throw new BusinessException(ErrorCode.FORBIDDEN_AUTHOR_ONLY);
        }

        if (!review.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("요청한 상품과 리뷰의 상품이 일치하지 않습니다.");
        }

        review.setDescription(req.getDescription());
        review.setRating(req.getRating());

        return PatchReviewResponse.from(review);
    }

    // 리뷰 삭제(사용자) 기능 구현
    @Transactional
    public DeleteReviewResponse DeleteReview(
            UserPrincipal principal, Long productId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다. id=" + reviewId));

        // 작성자 본인 여부 검증
        boolean isOwner = review.getUser().getId().equals(principal.getId());
        if (!isOwner) {
            throw new BusinessException(ErrorCode.FORBIDDEN_AUTHOR_ONLY);
        }

        if (!review.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("요청한 상품과 리뷰의 상품이 일치하지 않습니다.");
        }

        // 삭제 수행
        reviewRepository.deleteById(reviewId);

        return DeleteReviewResponse.from(review, "리뷰 삭제가 완료되었습니다.");
    }

    // 관리자 리뷰 리스트 조회 (키워드 검색 시)
    @Transactional(readOnly = true)
    public GetReviewListByAdminResponse getAdminReviewResponse(String keyword, Pageable pageable) {
        Page<Review> page = (keyword == null || keyword.isBlank())
                ? reviewRepository.findAll(pageable)
                : reviewRepository.searchByKeyword(keyword, pageable);
        return GetReviewListByAdminResponse.from(page);
    }

    // 리뷰 상세 정보 조회(관리자)
    @Transactional(readOnly = true)
    public GetReviewDetailByAdminResponse getReviewDetailByAdminResponse(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found."));
        return GetReviewDetailByAdminResponse.from(review);
    }

    // 리뷰 삭제(관리자)
    @Transactional
    public DeleteReviewByAdminResponse deleteReviewByAdmin(Long reviewId, String comment) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다. id=" + reviewId));
        reviewRepository.delete(review);
        return DeleteReviewByAdminResponse.from(review, "리뷰가 삭제되었습니다.", comment);
    }
}


