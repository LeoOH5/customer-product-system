package com.sparta.customerproductsystem.dto.orderdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderRequest {

    @NotBlank(message = "(필수) 상품명을 입력해주세요.")
    private String name;

    @NotBlank(message = "(필수) 구매할 상품 수량을 입력해주세요.")
    private int quantity;
}
