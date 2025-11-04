package com.sparta.customerproductsystem.jwt;

import com.sparta.customerproductsystem.jwt.dto.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("유효하지 않은 토큰입니다.");
        }
        return false;

    }


    public UserInfo getUserInfo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new UserInfo(
                claims.get("id", Long.class),
                claims.get("email", String.class),
                claims.get("name", String.class),
                claims.get("role", String.class)
        );

    }
}
