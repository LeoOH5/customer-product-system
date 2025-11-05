package com.sparta.customerproductsystem.dto.reviewdto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PatchReviewRequest {

    @NotBlank(message = "리뷰 내용은 비워둘 수 없습니다.")
    private final String description;

    @Min(value = 0, message = "rating은 0 이상이어야 합니다.")
    @Max(value = 5, message = "rating은 5 이하여야 합니다.")

    private final double rating;

    public PatchReviewRequest(String description, double rating) {
        this.description = description;
        this.rating = rating;
    }
}
