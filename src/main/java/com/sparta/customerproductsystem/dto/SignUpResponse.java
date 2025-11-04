package com.sparta.customerproductsystem.dto;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponse {
    private Long id;
    private String email;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt;

    public SignUpResponse(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name =  user.getName();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }
}
