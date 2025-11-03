package com.sparta.customerproductsystem.dto;

import com.sparta.customerproductsystem.domain.role.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PatchUserUpdateResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final LocalDateTime updatedAt;

    public PatchUserUpdateResponse(Long id, String email, String name, UserRole role, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.updatedAt = updatedAt;
    }
}
