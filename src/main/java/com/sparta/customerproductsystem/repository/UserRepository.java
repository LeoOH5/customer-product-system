package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
