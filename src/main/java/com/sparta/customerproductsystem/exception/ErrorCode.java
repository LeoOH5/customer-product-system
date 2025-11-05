package com.sparta.customerproductsystem.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    //400
    INVALID_QUERY_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터가 유효하지 않습니다."), //요청 파라미터가 유효하지 않습니다.
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."), //입력값이 유효하지 않습니다.
          // - AUTH
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "accessToken 값이 올바르지 않습니다."),
    MISSING_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "refreshToken 필드가 누락되었습니다."),
          // - USER
    INVALID_KEYWORD(HttpStatus.BAD_REQUEST, "검색 키워드가 유효하지 않습니다."),
    INVALID_UPDATE_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 수정 요청입니다."),
          // - PRODUCT
    INVALID_PRODUCT_ID(HttpStatus.BAD_REQUEST, "상품 ID가 올바르지 않습니다."),
    INVALID_PRODUCT_UPDATE(HttpStatus.BAD_REQUEST, "재고 수량은 0 이하로 수정할 수 없습니다."),
          // - ORDER
    INVALID_ORDER_STOCK(HttpStatus.BAD_REQUEST, "주문하시려는 상품의 재고가 부족합니다."),
          // - REVIEW
    INVALID_PRODUCT_REQUEST(HttpStatus.BAD_REQUEST, "요청 데이터가 유효하지 않습니다."),

    //401,
    MISSING_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Authorization 헤더에 Access Token이 없습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    TOKEN_EXPIRED_OR_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 리프레시 토큰입니다. 다시 로그인해주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),

    //403,
    FORBIDDEN_ADMIN_ONLY(HttpStatus.FORBIDDEN, "해당 요청은 관리자만 수행할 수 있습니다."),

    //404,
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 상품이 없습니다."),

    //409,
    USER_DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    //500
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
    public String getMessage() {
        return message;
    }
}
