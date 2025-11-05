package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Order;
import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.OrderRole;
import com.sparta.customerproductsystem.domain.role.UserRole;
import com.sparta.customerproductsystem.dto.orderdto.*;
import com.sparta.customerproductsystem.exception.BusinessException;
import com.sparta.customerproductsystem.exception.ErrorCode;
import com.sparta.customerproductsystem.repository.OrderRepository;
import com.sparta.customerproductsystem.repository.ProductRepository;
import com.sparta.customerproductsystem.repository.UserRepository;
import com.sparta.customerproductsystem.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
        checkProductStock(request.getQuantity(), product);

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

        // 삭제를 요청한 사용자가 Order user_id와 같은지 check
        checkUserId(order, user);

        return GetOrderResponse.from(order);
    }

    @Transactional
    public DeleteOrderResponse delete(Long orderId, UserPrincipal user) {

        Order order = findOrderById(orderId);

        // 삭제를 요청한 사용자가 Order user_id와 같은지 check
        checkUserId(order, user);

        order.delete(order.getStatus());
        order.getProduct().setTotalSales(order.getProduct().getStockQuantity(), -order.getQuantity());
        return new DeleteOrderResponse(order.getId(), order.getStatus());
    }

    @Transactional
    public GetOrderResponse update(Long orderId, CreateOrderRequest request, UserPrincipal user) {

        Order order = findOrderById(orderId);
        Product product = findProductByName(request.getName());

        // 수정할 상품 재고 check
        checkProductStock(request.getQuantity(), product);

        int amount = product.getPrice() * request.getQuantity();

        // 사용자 조회 시 해당 사용자 Order 건만 조회 가능
        // 사용자 일치하는지 check
        checkUserId(order, user);

        order.update(request.getQuantity(), amount, product);

        return GetOrderResponse.from(order);
    }

    public Users findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> BusinessException.of(ErrorCode.INVALID_USER));
    }

    public Order findOrderById(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(
                () -> BusinessException.of(ErrorCode.INVALID_QUERY_PARAMETER));
    }

    public Product findProductByName(String productName) {

        return productRepository.findByName(productName).orElseThrow(
                () -> BusinessException.of(ErrorCode.INVALID_PRODUCT_INSERT));
    }

    public void checkProductStock(int quantity, Product product) {

        //주문 가능 여부 check (재고 부족)
        if (quantity > product.getStockQuantity()) {
            throw BusinessException.of(ErrorCode.INVALID_ORDER_STOCK);
        }
    }

    public void checkUserId(Order order, UserPrincipal user) {

        if ((!order.getUser().getId().equals(user.getId())) && (!user.getRole().equals(UserRole.ADMIN))) {
            throw BusinessException.of(ErrorCode.FORBIDDEN_ADMIN_ONLY);
        }
    }
}
