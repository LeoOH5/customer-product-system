package com.sparta.customerproductsystem.dto.mapper;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.GetUserDetailResponse;
import com.sparta.customerproductsystem.dto.GetUserListResponse;
import com.sparta.customerproductsystem.dto.PatchUserUpdateResponse;

public class UserMapper {
    // 유저 리스트 조회 응답
    public static GetUserListResponse userList(Users users) {
        return new GetUserListResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getRole(),
                users.getCreatedAt()
        );
    }

    // 유저 상세정보 조회 응답
    public static GetUserDetailResponse userDetail(Users users) {
        return new GetUserDetailResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getRole(),
                users.getCreatedAt(),
                users.getUpdatedAt()
        );
    }

    // 유저 정보 수정 응답
    public static PatchUserUpdateResponse userUpdate(Users users) {
        return new PatchUserUpdateResponse(
                users.getId(),
                users.getEmail(),
                users.getName(),
                users.getRole(),
                users.getUpdatedAt()
        );
    }
}
