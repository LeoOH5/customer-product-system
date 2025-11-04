package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.SignUpRequest;
import com.sparta.customerproductsystem.dto.SignUpResponse;
import com.sparta.customerproductsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/user/register")
    public SignUpResponse signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse result = authService.saveUsers(signUpRequest);
        return result;
    }
}
