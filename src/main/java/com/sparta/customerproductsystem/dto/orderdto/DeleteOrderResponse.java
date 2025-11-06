package com.sparta.customerproductsystem.dto.orderdto;

import com.sparta.customerproductsystem.domain.role.OrderRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteOrderResponse {
    private final Long id;
    private final OrderRole role;

}