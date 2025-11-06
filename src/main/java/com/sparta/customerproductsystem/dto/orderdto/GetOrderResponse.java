package com.sparta.customerproductsystem.dto.orderdto;

import com.sparta.customerproductsystem.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetOrderResponse {

    private final Long id;
    private final String userName;
    private final String productName;
    private final int quantity;
    private final int amount;
    private final LocalDateTime orderDate;

    public static GetOrderResponse from(Order order) {
        return new GetOrderResponse(order.getId(),
                order.getUser().getName(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getAmount(),
                order.getCreatedAt());
    }
}