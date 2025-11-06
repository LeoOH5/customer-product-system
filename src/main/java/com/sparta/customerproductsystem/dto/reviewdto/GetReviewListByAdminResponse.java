package com.sparta.customerproductsystem.dto.reviewdto;

import com.sparta.customerproductsystem.domain.entity.Review;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetReviewListByAdminResponse {

    private final List<ReviewItem> reviewList;  // 리뷰 목록 데이터
    private final int page;                     // 현재 페이지 번호
    private final int size;                     // 페이지당 개수
    private final long totalElements;           // 전체 데이터 수
    private final int totalPages;               // 전체 페이지 수
    private final boolean first;                // 첫 페이지 여부
    private final boolean last;                 // 마지막 페이지 여부

    public GetReviewListByAdminResponse(
            List<ReviewItem> reviewList, int page, int size,
            long totalElements, int totalPages, boolean first, boolean last)
    {
        this.reviewList = reviewList;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
    }


    // 내부 클래스: 리뷰 한 건 데이터
    @Getter
    public static class ReviewItem {
        private final Long id;
        private final String productName;
        private final String userName;
        private final double rating;
        private final String description;
        private final LocalDateTime createdAt;

        public ReviewItem(Long id, String productName, String userName,
                          double rating, String description, LocalDateTime createdAt) {
            this.id = id;
            this.productName = productName;
            this.userName = userName;
            this.rating = rating;
            this.description = description;
            this.createdAt = createdAt;
        }

        // 엔티티 → DTO 변환
        public static ReviewItem from(Review review) {
            return new ReviewItem(
                    review.getId(),
                    review.getProduct().getName(),
                    review.getUser().getName(),
                    review.getRating(),
                    review.getDescription(),
                    review.getCreatedAt()
            );
        }
    }

    // Page<Review> → GetReviewListByAdminResponse 변환
    public static GetReviewListByAdminResponse from(Page<Review> page) {
        List<ReviewItem> items = page.getContent().stream()
                .map(ReviewItem::from)
                .collect(Collectors.toList());

        return new GetReviewListByAdminResponse(
                items,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}