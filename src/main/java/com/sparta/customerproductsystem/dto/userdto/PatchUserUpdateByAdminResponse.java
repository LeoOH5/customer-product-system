package com.sparta.customerproductsystem.dto.userdto;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PatchUserUpdateByAdminResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final LocalDateTime updatedAt;

    public PatchUserUpdateByAdminResponse(Long id, String email, String name, UserRole role, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.updatedAt = updatedAt;
    }

    public static PatchUserUpdateByAdminResponse from(Users users) {
        return new PatchUserUpdateByAdminResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getRole(),
                users.getUpdatedAt()
        );
    }
}
