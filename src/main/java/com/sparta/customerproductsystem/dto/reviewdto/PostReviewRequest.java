package com.sparta.customerproductsystem.dto.reviewdto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReviewRequest {
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;

    @Min(value = 0, message = "rating은 0.0 이상이어야 합니다.")
    @Max(value = 5, message = "rating은 5.0 이하여야 합니다.")
    private double rating;
}
