package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.orderdto.*;
import com.sparta.customerproductsystem.exception.CommonResponse;
import com.sparta.customerproductsystem.security.UserPrincipal;
import com.sparta.customerproductsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponse<CreateOrderResponse>> createOrder(@RequestBody CreateOrderRequest request,
                                                                          @AuthenticationPrincipal UserPrincipal user) {

        CreateOrderResponse result = orderService.save(request, user);

        CommonResponse<CreateOrderResponse> body = CommonResponse.<CreateOrderResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(body);

    }

    @PreAuthorize("#user.id == principal.id or hasRole('ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<GetOrderResponse>> getDetailOrders(@PathVariable Long orderId,
                                                            @AuthenticationPrincipal UserPrincipal user) {

        GetOrderResponse result = orderService.getOne(orderId, user);

        CommonResponse<GetOrderResponse> body = CommonResponse.<GetOrderResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @PreAuthorize("#user.id == principal.id or hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<CommonResponse<DeleteOrderResponse>> deleteOrder(@PathVariable Long orderId,
                                                           @AuthenticationPrincipal UserPrincipal user) {

        DeleteOrderResponse result = orderService.delete(orderId, user);

        CommonResponse<DeleteOrderResponse> body = CommonResponse.<DeleteOrderResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PreAuthorize("#user.id == principal.id or hasRole('ADMIN')")
    @PatchMapping("/{orderId}")
    public ResponseEntity<CommonResponse<GetOrderResponse>> patchOrder(@PathVariable Long orderId,
                                                       @RequestBody CreateOrderRequest request,
                                                       @AuthenticationPrincipal UserPrincipal user) {

        GetOrderResponse result = orderService.update(orderId, request, user);

        CommonResponse<GetOrderResponse> body = CommonResponse.<GetOrderResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(body);

    }
}
