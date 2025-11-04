package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Order;
import com.sparta.customerproductsystem.domain.entity.Product;
import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.OrderRole;
import com.sparta.customerproductsystem.dto.orderdto.*;
import com.sparta.customerproductsystem.repository.OrderRepository;
import com.sparta.customerproductsystem.repository.ProductRepository;
import com.sparta.customerproductsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateOrderResponse save(CreateOrderRequest request/*, Users user*/) {
        // 상품명 존재 여부 check
        Product product = findProductByName(request.getName());
        //주문 가능 여부 check (재고 부족)
        if(!checkProductStock(request.getQuantity(), product)) {}
        int amount = product.getPrice() * request.getQuantity();
        //테스트용 User
        Users user = userRepository.findById(1L).orElseThrow(
                () -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        Order order = new Order(user, product, request.getQuantity(), amount);
        order.setStatus(OrderRole.OK);
        Order savedOrder = orderRepository.save(order);

        return CreateOrderResponse.from(savedOrder);

    }

    @Transactional(readOnly = true)
    public GetOrderResponse getOne(Long orderId/*, Users user*/) {
        Order order = findOrderById(orderId);
        //테스트용 User
        Users user = userRepository.findById(1L).orElseThrow(
                () -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        // 관리자 조회 시 전체 사용자의 Order 조회 가능
//        if(user.getRole().equals(UserRole.ADMIN)) {
//            return GetOrderResponse.from(order);
//        }
        // 사용자 조회 시 해당 사용자 Order 건만 조회 가능
        // 사용자 일치하는지 check
        if (!user.getId().equals(order.getUser().getId())) {
            throw new IllegalArgumentException("해당 요청은 관리자만 수행할 수 있습니다.");
        }
        return GetOrderResponse.from(order);
    }

    @Transactional
    public DeleteOrderResponse delete(Long orderId/*, Users user*/) {
        Order order = findOrderById(orderId);
        //테스트용 User
        Users user = userRepository.findById(1L).orElseThrow(
                () -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        // 삭제를 요청한 사용자가 Order user_id와 같은지 check
        if (!user.getId().equals(order.getUser().getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        Long id = order.getId();
        orderRepository.delete(order);
        return new DeleteOrderResponse(id, OrderRole.CANCEL);
    }

    @Transactional
    public GetOrderResponse update(Long orderId, CreateOrderRequest request/*, Users user*/) {
        Order order = findOrderById(orderId);
        Product product = findProductByName(request.getName());
        // 수정할 상품 재고 check
        if(!checkProductStock(request.getQuantity(), product)) {
            // 예외 처리
        }
        int amount = product.getPrice() * request.getQuantity();

        //테스트용 User
        Users user = userRepository.findById(1L).orElseThrow(
                () -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        // 관리자 조회 시 전체 사용자의 Order 조회 가능
//        if(user.getRole().equals(UserRole.ADMIN)) {
//            return GetOrderResponse.from(order);
//        }
        // 사용자 조회 시 해당 사용자 Order 건만 조회 가능
        // 사용자 일치하는지 check
        if (!user.getId().equals(order.getUser().getId())) {
            throw new IllegalArgumentException("해당 요청은 관리자만 수행할 수 있습니다.");
        }
        order.update(request.getQuantity(), amount, product);

        return GetOrderResponse.from(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("요청 파라미터가 유효하지 않습니다."));
    }

    public Product findProductByName(String productName) {
        return productRepository.findByName(productName).orElseThrow(
                () -> new IllegalStateException("올바른 상품명을 입력해주세요."));
    }

    public boolean checkProductStock(int quantity, Product product) {
        //주문 가능 여부 check (재고 부족)
        if (quantity > product.getStock()) {
            throw new IllegalStateException("재고가 부족합니다. 구매 수량을 수정해주세요.");
        }
        return true;
    }
}
