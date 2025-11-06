package com.sparta.customerproductsystem.dto.userdto;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserListResponse { // 유저 리스트 조회
    private final Long id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final LocalDateTime createdAt;

    public GetUserListResponse(Long id, String email, String name, UserRole role, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }
    public static GetUserListResponse from(Users users) {
        return new GetUserListResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getRole(),
                users.getCreatedAt()
        );
    }
}
