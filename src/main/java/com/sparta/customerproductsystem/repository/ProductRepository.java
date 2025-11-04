package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 존재 여부만 확인 쿼리 최소화
    boolean existsByName(String name);
}
