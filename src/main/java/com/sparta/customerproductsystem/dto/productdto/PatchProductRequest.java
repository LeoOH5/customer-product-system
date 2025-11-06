package com.sparta.customerproductsystem.dto.productdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchProductRequest {

    private String name;

    // null을 체크하기 위해 래퍼클래스 사용
    private Integer price;
    private Integer stockQuantity;

}
