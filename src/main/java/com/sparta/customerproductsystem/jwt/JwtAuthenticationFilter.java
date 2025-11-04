package com.sparta.customerproductsystem.jwt;

import com.sparta.customerproductsystem.jwt.dto.UserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/user/register") ||
                request.getRequestURI().startsWith("/user/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authToken == null || !authToken.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"토큰이 없거나 형식이 잘못되었습니다.\"}");
            return;
        }
        String token = authToken.substring(7);

        if (!jwtUtils.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"Access Token expired\"}");
            return;
        }

        UserInfo userInfo = jwtUtils.getUserInfo(token);

        // 사용자의 권한 목록을 담을 컬렉션 사용
        List<GrantedAuthority> authorities = new ArrayList<>();
        // JWT에서 꺼낸 사용자 role을 Spring Security 권한 규칙에 맞게 등록
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userInfo.getRole()));

        Authentication auth =
                // 기존 emptyList 반환 -> 비어있는 사용자로 등록된 부분을 실제 권한을 담아 반영한 사용자로 반환
                new UsernamePasswordAuthenticationToken(userInfo, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
