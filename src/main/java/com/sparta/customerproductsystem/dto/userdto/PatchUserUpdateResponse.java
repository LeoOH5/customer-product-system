package com.sparta.customerproductsystem.dto.userdto;

import com.sparta.customerproductsystem.domain.entity.Users;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PatchUserUpdateResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final LocalDateTime updatedAt;

    public PatchUserUpdateResponse(Long id, String email, String name, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public static PatchUserUpdateResponse from(Users users) {
        return new PatchUserUpdateResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getUpdatedAt()
        );
    }
}
