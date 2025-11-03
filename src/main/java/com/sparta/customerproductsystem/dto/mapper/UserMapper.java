package com.sparta.customerproductsystem.dto.mapper;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.GetUserDetailResponse;
import com.sparta.customerproductsystem.dto.GetUserListResponse;

public class UserMapper {
    public static GetUserListResponse userList(Users users) {
        return new GetUserListResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getRole(),
                users.getCreatedAt()
        );
    }
}
