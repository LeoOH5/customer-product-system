package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.GetUserDetailResponse;
import com.sparta.customerproductsystem.dto.GetUserListResponse;
import com.sparta.customerproductsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 유저 리스트 조회
    @GetMapping
    public ResponseEntity<List<GetUserListResponse>> getUserList(
            @RequestParam(required = false) String keyword
    ) {
        List<GetUserListResponse> body = userService.userList(keyword);
        return ResponseEntity.ok(body);
    }

    // 유저 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDetailResponse> getUserDetail(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }
}
