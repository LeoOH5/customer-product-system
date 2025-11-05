package com.sparta.customerproductsystem.dto.reviewdto;

import lombok.Getter;

@Getter
public class DeleteReviewByAdminRequest {
    private final String comment;

    public DeleteReviewByAdminRequest(String comment) {
        this.comment = comment;
    }
}
