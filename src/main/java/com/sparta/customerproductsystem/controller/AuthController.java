package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.*;
import com.sparta.customerproductsystem.security.UserPrincipal;
import com.sparta.customerproductsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //유저 회원가입
    @PostMapping("/user/auth/register")
    public ResponseEntity<SignUpResponse> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse result = authService.saveUsers(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //유저 로그인
    @PostMapping("/user/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse result = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //토큰 재발급
    @PostMapping("/user/auth/refresh")
    public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest refreshRequest) {
        RefreshResponse result = authService.refresh(refreshRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //관리자권한 회원 추가
    @PostMapping("/admin/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminCreateUserResponse> adminUserSave(@Valid @RequestBody AdminCreateUserRequest adminCreateUserRequest) {
        AdminCreateUserResponse result = authService.adminUserSave(adminCreateUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //로그아웃
    @DeleteMapping("/user/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        authService.logout(userPrincipal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
