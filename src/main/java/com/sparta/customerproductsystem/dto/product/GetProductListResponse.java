package com.sparta.customerproductsystem.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
// 페이징 확장을 위해 따로 List Dto 생성
public class GetProductListResponse {

    private final List<GetProductResponse> content;

}
