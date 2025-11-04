package com.sparta.customerproductsystem.repository;

import com.sparta.customerproductsystem.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
