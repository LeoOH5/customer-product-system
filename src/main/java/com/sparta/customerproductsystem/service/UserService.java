package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.dto.GetUserDetailResponse;
import com.sparta.customerproductsystem.dto.GetUserListResponse;
import com.sparta.customerproductsystem.dto.mapper.UserMapper;
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
        return users.stream().map(UserMapper::userList).toList();
    }

    // 유저 상세 정보 조회
    @Transactional(readOnly = true)
    public GetUserDetailResponse findUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserMapper.userDetail(user);
    }
}
