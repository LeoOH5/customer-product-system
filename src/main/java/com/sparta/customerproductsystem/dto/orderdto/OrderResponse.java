package com.sparta.customerproductsystem.dto.orderdto;

import com.sparta.customerproductsystem.domain.entity.Order;
import com.sparta.customerproductsystem.domain.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderResponse {

    private final Long id;
    private final Long user_id;
    private final Long product_id;
    private final int quantity;
    private final LocalDateTime order_date;
    private final int amount;
    private final String status;

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(),
                order.getUser().getId(),
                order.getProduct().getId(),
                order.getQuantity(),
                order.getCreatedAt(),
                order.getAmount(),
                order.getStatus());
    }
}
