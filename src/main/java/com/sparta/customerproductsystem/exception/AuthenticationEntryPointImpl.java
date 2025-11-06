package com.sparta.customerproductsystem.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Map;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // ErrorCode 기반 내용 처리 및 활용
        ErrorCode error = ErrorCode.TOKEN_EXPIRED_OR_INVALID;
        response.setStatus(error.getStatus().value());
        // 응답을 JSON 형태로 반환
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(MAPPER.writeValueAsString(Map.of(
                "success", false,
                "code", error.name(),
                "message", error.getMessage()
        )));
    }
}