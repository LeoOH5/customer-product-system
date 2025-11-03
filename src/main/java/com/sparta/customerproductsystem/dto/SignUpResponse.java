package com.sparta.customerproductsystem.dto;

import com.sparta.customerproductsystem.domain.entity.Users;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponse {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private LocalDateTime createAt;

    public SignUpResponse(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name =  user.getName();
        this.phone = user.getPhone();
        this.createAt = user.getCreatedAt();
    }
}
