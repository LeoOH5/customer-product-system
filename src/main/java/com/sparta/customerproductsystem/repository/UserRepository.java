package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    // 유저 리스트 조회 - 검색
    List<Users> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String keyword, String keyword1);

    Boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
