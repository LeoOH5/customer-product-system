package com.sparta.customerproductsystem.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component //빈등록해서 사용하기
public class JwtUtils {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;


    public JwtUtils(@Value("${JWT_SECRET_KEY}") String secret,
                    @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
                    @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(long userId, String email, String name, String role) {
        return generateToken(userId, email, name, role, accessTokenExpiration);
    }

    public String generateRefreshToken(long userId, String email, String name, String role) {
        return generateToken(userId, email, name, role, refreshTokenExpiration);
    }

    private String generateToken(Long userId, String email, String name, String role, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .claim("id", userId)
                .claim("email", email)
                .claim("name", name)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        }
    }

    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        return claims.get("id", Long.class);
    }

    public String getEmail(String token) {
        Claims claims = parseToken(token);
        return claims.get("email", String.class);
    }

    public String getName(String token) {
        Claims claims = parseToken(token);
        return claims.get("name", String.class);
    }

    public String getRole(String token) {
        Claims claims = parseToken(token);
        return claims.get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
