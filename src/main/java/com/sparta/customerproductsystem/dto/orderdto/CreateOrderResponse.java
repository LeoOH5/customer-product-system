package com.sparta.customerproductsystem.dto.orderdto;

import com.sparta.customerproductsystem.domain.entity.Order;
import com.sparta.customerproductsystem.domain.role.OrderRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateOrderResponse {

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final LocalDateTime orderDate;
    private final int quantity;
    private final int amount;
    private final OrderRole role;

    public static CreateOrderResponse from(Order order) {
        return new CreateOrderResponse(order.getId(),
                order.getUser().getId(),
                order.getProduct().getId(),
                order.getCreatedAt(),
                order.getAmount(),
                order.getQuantity(),
                order.getStatus());
    }
}