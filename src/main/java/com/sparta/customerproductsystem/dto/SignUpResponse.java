package com.sparta.customerproductsystem.dto;

import com.sparta.customerproductsystem.domain.entity.Users;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final LocalDateTime createAt;

    public SignUpResponse(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name =  user.getName();
        this.createAt = user.getCreatedAt();
    }
}
