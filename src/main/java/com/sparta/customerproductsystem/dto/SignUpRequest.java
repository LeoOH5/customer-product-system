package com.sparta.customerproductsystem.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
}
