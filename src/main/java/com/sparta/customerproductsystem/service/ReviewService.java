package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Review;
import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.reviewdto.PostReviewRequest;
import com.sparta.customerproductsystem.dto.reviewdto.PostReviewResponse;
import com.sparta.customerproductsystem.repository.ProductRepository;
import com.sparta.customerproductsystem.repository.ReviewRepository;
import com.sparta.customerproductsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰등록
    @Transactional
    public PostReviewResponse createReview(Long productId, PostReviewRequest req) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. id=" + productId));

        Users user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + req.getUserId()));

        if (reviewRepository.existsByUserAndProduct(user, product)) {
            throw new IllegalStateException("이미 이 상품에 대한 리뷰를 작성했습니다.");
        }

        Review review = Review.create(user, product, req.getRating());

        Review saved = reviewRepository.save(review);

        return PostReviewResponse.from(saved);
    }
}


