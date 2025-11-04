package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.*;
import com.sparta.customerproductsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //유저 회원가입
    @PostMapping("/user/register")
    public SignUpResponse signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse result = authService.saveUsers(signUpRequest);
        return result;
    }

    //유저 로그인
    @PostMapping("/user/auth/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);

    }

    //관리자권한 회원 추가
    @PostMapping("/admin/user")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminCreateUserResponse adminUserSave(@Valid @RequestBody AdminCreateUserRequest adminCreateUserRequest){
        return authService.adminUserSave(adminCreateUserRequest);
    }
}
