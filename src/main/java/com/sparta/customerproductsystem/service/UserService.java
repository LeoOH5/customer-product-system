package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.*;
import com.sparta.customerproductsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 유저 리스트 페이지 조회
    @Transactional(readOnly = true)
    public GetUserPageResponse getUserListPage(String keyword, Pageable pageable) {
        Page<Users> users = userRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
        return GetUserPageResponse.from(users);
    }

    // 유저 정보 수정 (일반유저)
    @Transactional
    public PatchUserUpdateResponse updateUser(Long id, PatchUserUpdateRequest request) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        return PatchUserUpdateResponse.from(user);
    }

    // 유저 정보 수정 (관리자)
    @Transactional
    public PatchUserUpdateByAdminResponse updateUserByAdmin (Long id, PatchUserUpdateByAdminRequest updateRequest) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updateRequest.getName() != null && !updateRequest.getName().isBlank()) {
            user.setName(updateRequest.getName());
        }
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isBlank()) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getRole() != null) {
            user.setRole(updateRequest.getRole());
        }

        return PatchUserUpdateByAdminResponse.from(user);
    }
}
