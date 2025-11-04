package com.sparta.customerproductsystem.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;


    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
                    @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(long userId, String email, String userName, String role) {
        return generateToken(userId, email, userName, role, accessTokenExpiration);
    }

    public String generateRefreshToken(long userId, String email, String userName, String role) {
        return generateToken(userId, email, userName, role, refreshTokenExpiration);
    }

    private String generateToken(Long userId, String email, String name, String role, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .claim("id", userId)
                .claim("email", email)
                .claim("name", name)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }
}
