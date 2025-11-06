package com.sparta.customerproductsystem.dto.userdto;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserDetailResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public GetUserDetailResponse(
            Long id, String email, String name, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static GetUserDetailResponse from(Users users) {
        return new GetUserDetailResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getRole(),
                users.getCreatedAt(),
                users.getUpdatedAt()
        );
    }
}
