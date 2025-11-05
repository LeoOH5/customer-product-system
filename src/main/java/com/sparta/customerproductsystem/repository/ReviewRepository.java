package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Review;
import com.sparta.customerproductsystem.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Boolean existsByUserAndProduct(Users user, Product product);

    List<Review> findByProductId(Long productId);

    Optional<Review> findById(Long reviewId);
}
