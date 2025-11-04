package com.sparta.customerproductsystem.dto.productdto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private int price;

    @NotNull
    private int stockQuantity;

    @NotNull
    private String category;

    private String imageUrl;

}
