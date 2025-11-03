package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.orderdto.DeleteOrderResponse;
import com.sparta.customerproductsystem.dto.orderdto.OrderRequest;
import com.sparta.customerproductsystem.dto.orderdto.OrderResponse;
import com.sparta.customerproductsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request,
                                                     @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, user));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getDetailOrders(@PathVariable Long orderId,
                                                               @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOne(orderId, user));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<DeleteOrderResponse> deleteOrder(@PathVariable Long orderId,
                                                           @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.delete(orderId, user));
    }
}
