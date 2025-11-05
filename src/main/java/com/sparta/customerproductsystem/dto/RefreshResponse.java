package com.sparta.customerproductsystem.dto;

import lombok.Getter;

@Getter
public class RefreshResponse {

    private String accessToken;
    private String refreshToken;

    public RefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
