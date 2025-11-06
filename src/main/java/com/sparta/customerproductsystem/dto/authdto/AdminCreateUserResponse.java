package com.sparta.customerproductsystem.dto.authdto;

import com.sparta.customerproductsystem.domain.entity.Users;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminCreateUserResponse {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;

    public AdminCreateUserResponse(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.role = user.getRole().name();
        this.createdAt = user.getCreatedAt();
    }
}
