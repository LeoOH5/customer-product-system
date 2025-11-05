package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Order;
import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.OrderRole;
import com.sparta.customerproductsystem.domain.role.UserRole;
import com.sparta.customerproductsystem.dto.orderdto.*;
import com.sparta.customerproductsystem.repository.OrderRepository;
import com.sparta.customerproductsystem.repository.ProductRepository;
import com.sparta.customerproductsystem.repository.UserRepository;
import com.sparta.customerproductsystem.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateOrderResponse save(CreateOrderRequest request, UserPrincipal user) {

        // 상품명 존재 여부 check
        Product product = findProductByName(request.getName());
        //주문 가능 여부 check (재고 부족)
        try {
            checkProductStock(request.getQuantity(), product);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());// 이후 exceptionHandler로 처리
        }
        int amount = product.getPrice() * request.getQuantity();
        Users userInfo = findUserById(user.getId());

        Order order = new Order(userInfo, product, request.getQuantity(), amount, OrderRole.OK);
        Order savedOrder = orderRepository.save(order);

        product.setTotalSales(product.getStockQuantity(), request.getQuantity());

        return CreateOrderResponse.from(savedOrder);

    }

    @Transactional(readOnly = true)
    public GetOrderResponse getOne(Long orderId, UserPrincipal user) {

        Order order = findOrderById(orderId);
        Users userInfo = findUserById(user.getId());

        // 관리자 조회 시 전체 사용자의 Order 조회 가능
        if(user.getRole().equals(UserRole.ADMIN)) {
            return GetOrderResponse.from(order);
        }
        // 사용자 조회 시 해당 사용자 Order 건만 조회 가능
        // 삭제를 요청한 사용자가 Order user_id와 같은지 check
        try {
            checkUserId(order, userInfo);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());// 이후 exceptionHandler로 처리
        }
        return GetOrderResponse.from(order);
    }

    @Transactional
    public DeleteOrderResponse delete(Long orderId, UserPrincipal user) {

        Order order = findOrderById(orderId);
        Users userInfo = findUserById(user.getId());

        // 삭제를 요청한 사용자가 Order user_id와 같은지 check
        try {
            checkUserId(order, userInfo);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());// 이후 exceptionHandler로 처리
        }
        order.delete(order.getStatus());
        order.getProduct().setTotalSales(order.getProduct().getStockQuantity(), -order.getQuantity());
        return new DeleteOrderResponse(order.getId(), order.getStatus());
    }

    @Transactional
    public GetOrderResponse update(Long orderId, CreateOrderRequest request, UserPrincipal user) {

        Order order = findOrderById(orderId);
        Product product = findProductByName(request.getName());
        Users userInfo = findUserById(user.getId());

        // 수정할 상품 재고 check
        try {
            checkProductStock(request.getQuantity(), product);
        } catch (IllegalStateException e) {
            // ExceptionHandler로 처리
        }
        int amount = product.getPrice() * request.getQuantity();

        // 관리자 조회 시 전체 사용자의 Order 조회 가능
        if(user.getRole().equals(UserRole.ADMIN)) {
            return GetOrderResponse.from(order);
        }

        // 사용자 조회 시 해당 사용자 Order 건만 조회 가능
        // 사용자 일치하는지 check
        try {
            checkUserId(order, userInfo);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());// 이후 exceptionHandler로 처리
        }
        order.update(request.getQuantity(), amount, product);

        return GetOrderResponse.from(order);
    }

    public Users findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("User not found"));
    }

    public Order findOrderById(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("요청 파라미터가 유효하지 않습니다."));
    }

    public Product findProductByName(String productName) {

        return productRepository.findByName(productName).orElseThrow(
                () -> new IllegalStateException("올바른 상품명을 입력해주세요."));
    }

    public void checkProductStock(int quantity, Product product) {

        //주문 가능 여부 check (재고 부족)
        if (quantity > product.getStockQuantity()) {
            throw new IllegalStateException("주문하시려는 상품의 재고가 부족합니다.");
        }
    }

    public void checkUserId(Order order, Users user) {

        if (!user.getId().equals(order.getUser().getId())) {
            throw new IllegalArgumentException("해당 요청은 관리자만 수행할 수 있습니다.");
        }
    }
}
