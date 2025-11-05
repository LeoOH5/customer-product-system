package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Review;
import com.sparta.customerproductsystem.domain.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Boolean existsByUserAndProduct(Users user, Product product);

    List<Review> findByProductId(Long productId);

    Optional<Review> findById(Long reviewId);

    // 전체 리뷰 페이징 조회 (N+1 방지를 위해 product,user 즉시 로딩)
    @EntityGraph(attributePaths = {"product", "user"})
    Page<Review> findAll(Pageable pageable);

    // 키워드 검색: description OR product.name (대소문자 무시)
    @EntityGraph(attributePaths = {"product", "user"})
    Page<Review> findByDescriptionContainingIgnoreCaseOrProduct_NameContainingIgnoreCase(
            String description, String productName, Pageable pageable
    );

    // 하나의 키워드를 두개의 필드에 동일 적용
    default Page<Review> searchByKeyword(String keyword, Pageable pageable) {
        return findByDescriptionContainingIgnoreCaseOrProduct_NameContainingIgnoreCase(
                keyword, keyword, pageable
        );
    }

}
