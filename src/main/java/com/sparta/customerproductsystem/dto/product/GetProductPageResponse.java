package com.sparta.customerproductsystem.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetProductPageResponse {

    private final List<GetProductResponse> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private int size;
    private boolean first;
    private boolean last;

    public static GetProductPageResponse of(List<GetProductResponse> content, Page<?> page) {
        return new GetProductPageResponse(
                content,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );

    }}
