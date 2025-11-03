package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Order;
import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.orderdto.OrderRequest;
import com.sparta.customerproductsystem.dto.orderdto.OrderResponse;
import com.sparta.customerproductsystem.repository.OrderRepository;
import com.sparta.customerproductsystem.repository.ProductRepository;
import com.sparta.customerproductsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse save(OrderRequest request, Users user) {
        // 상품명 존재 여부 check
        String name = request.getName();
        Product product = productRepository.findByName(name).orElseThrow(
                () -> new IllegalStateException("올바른 상품명을 입력해주세요."));
        //주문 가능 여부 check (재고 부족)
        if(request.getQuantity() > product.getStock()) {
            throw new IllegalStateException("재고가 부족합니다. 구매 수량을 수정해주세요.");
        }
        String status = "OK";

        int amount = product.getPrice() * request.getQuantity();

        Order order = new Order(user, product, request.getQuantity(), amount, status);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);

    }

    @Transactional(readOnly = true)
    public OrderResponse getOne(Long orderId, Users user) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("요청 파라미터가 유효하지 않습니다."));

        // 관리자 조회 시 전체 사용자의 Order 조회 가능
        if(user.getRole().equals("ADMIN")) {
            return OrderResponse.from(order);
        }

    }
}
