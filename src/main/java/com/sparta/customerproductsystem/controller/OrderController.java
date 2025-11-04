package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.orderdto.*;
import com.sparta.customerproductsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request,
                                                                         @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, user));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponse> getDetailOrders(@PathVariable Long orderId,
                                                            @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOne(orderId, user));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteOrderResponse> deleteOrder(@PathVariable Long orderId,
                                                           @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.delete(orderId, user));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<GetOrderResponse> patchOrder(@PathVariable Long orderId,
                                                       @RequestBody CreateOrderRequest request,
                                                         @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.update(orderId, request, user));
    }
}
