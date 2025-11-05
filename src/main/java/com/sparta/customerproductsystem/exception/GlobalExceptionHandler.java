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
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex,
                                                                 HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(LocalDateTime.now(),
                ex.getErrorCode().getStatus().value(),
                ex.getErrorCode().getStatus().getReasonPhrase(),
                ex.getErrorCode().getStatus().name(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(response);
    }

}
