package com.sparta.customerproductsystem.exception;

import com.sparta.customerproductsystem.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<ErrorDetail>> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        var code = ex.getErrorCode();

        ErrorDetail detail = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .status(code.getStatus().value())
                .error(code.getStatus().getReasonPhrase())
                .errorCode(code.name())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        CommonResponse<ErrorDetail> body = CommonResponse.<ErrorDetail>builder()
                .data(detail)
                .build();

        return ResponseEntity.status(code.getStatus()).body(body);

    }

}
