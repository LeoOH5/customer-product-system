package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDetailRepository extends JpaRepository<Users, Long> {
    Boolean existsByEmail(String email);
}
