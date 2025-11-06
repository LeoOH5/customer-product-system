package com.sparta.customerproductsystem.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Map;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        // ErrorCode 기반 내용 처리 및 활용
        ErrorCode error = ErrorCode.FORBIDDEN_ADMIN_ONLY;
        response.setStatus(error.getStatus().value());
        // 응답을 JSON 형태로 반환
        response.setContentType("application/json;charset=UTF-8");
        // JSON 형태로 응답 바디를 구성
        Map<String, Object> body = Map.of(
                "success", false,
                "code", error.name(),
                "message", error.getMessage()
        );
        response.getWriter().write(MAPPER.writeValueAsString(body));
    }
}