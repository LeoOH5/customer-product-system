package com.sparta.customerproductsystem.dto.authdto;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final LocalDateTime createdAt;

    public SignUpResponse(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name =  user.getName();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }
}
