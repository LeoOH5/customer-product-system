package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.RefreshToken;
import com.sparta.customerproductsystem.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
