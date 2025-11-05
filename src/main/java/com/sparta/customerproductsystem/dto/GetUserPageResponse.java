package com.sparta.customerproductsystem.dto;

import com.sparta.customerproductsystem.domain.entity.Users;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class GetUserPageResponse {
    private final List<GetUserListResponse> userList;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;
    private final int size;
    private final boolean first;
    private final boolean last;

    public GetUserPageResponse(List<GetUserListResponse> userList, int currentPage, int totalPages, long totalElements, int size, boolean first, boolean last) {
        this.userList = userList;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.first = first;
        this.last = last;
    }

    // Page<Users> → GetUserPageResponse 변환 메서드 추가
    public static GetUserPageResponse from(Page<Users> usersPage) {
        List<GetUserListResponse> userList = usersPage.getContent()
                .stream()
                .map(GetUserListResponse::from)
                .toList();

        return new GetUserPageResponse(
                userList,
                usersPage.getNumber(),
                usersPage.getTotalPages(),
                usersPage.getTotalElements(),
                usersPage.getSize(),
                usersPage.isFirst(),
                usersPage.isLast()
        );
    }
}
