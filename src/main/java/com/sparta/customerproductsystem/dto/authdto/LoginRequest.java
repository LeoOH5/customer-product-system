package com.sparta.customerproductsystem.dto.authdto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    private String password;
}
