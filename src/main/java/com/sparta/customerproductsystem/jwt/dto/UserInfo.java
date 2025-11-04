package com.sparta.customerproductsystem.jwt.dto;

import lombok.Getter;

@Getter
public class UserInfo {
    private long id;
    private String email;
    private String name;
    private String role;


    public UserInfo(long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }
}