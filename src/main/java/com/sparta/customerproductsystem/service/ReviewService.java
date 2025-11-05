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
}


