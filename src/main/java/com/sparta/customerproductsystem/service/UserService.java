package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.GetUserDetailResponse;
import com.sparta.customerproductsystem.dto.GetUserListResponse;
import com.sparta.customerproductsystem.dto.PatchUserUpdateRequest;
import com.sparta.customerproductsystem.dto.PatchUserUpdateResponse;
import com.sparta.customerproductsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 유저 리스트 조회 - 검색
    @Transactional(readOnly = true)
    public List<GetUserListResponse> userList(String keyword) {
        List<Users> users;
        if (keyword == null || keyword.isBlank()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        }
        return users.stream()
                .map(GetUserListResponse::from)
                .toList();
    }

    // 유저 상세 정보 조회
    @Transactional(readOnly = true)
    public GetUserDetailResponse findUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return GetUserDetailResponse.from(user);
    }

    // 유저 정보 수정
    @Transactional
    public PatchUserUpdateResponse updateUser(Long id, PatchUserUpdateRequest request) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        return PatchUserUpdateResponse.from(user);
    }
}
