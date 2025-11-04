package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.*;
import com.sparta.customerproductsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 유저 리스트 조회
    // params는 Mapping 조건을 지정하는 개념
    @GetMapping(params = {"!page", "!size"})
    public ResponseEntity<List<GetUserListResponse>> getUserList(
            @RequestParam(required = false) String keyword
    ) {
        List<GetUserListResponse> body = userService.userList(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 리스트 페이징 조회
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<GetUserPageResponse> getUserListPage(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        GetUserPageResponse body = userService.getUserListPage(keyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 상세 정보 조회
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')") // 본인 또는 관리자 권한만 상세정보 조회 가능
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDetailResponse> getUserDetail(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }

    // 유저 정보 수정 (일반)
    @PatchMapping("/{id}")
    public ResponseEntity<PatchUserUpdateResponse> patchUserUpdate(
            @PathVariable Long id, @RequestBody PatchUserUpdateRequest updateRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, updateRequest));
    }

    // 유저 정보 수정 (관리자 접근 페이지)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admins/{id}")
    public ResponseEntity<PatchUserUpdateByAdminResponse> patchUserUpdateByAdmin(
            @PathVariable Long id, @RequestBody PatchUserUpdateByAdminRequest updateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserByAdmin(id, updateRequest));
    }
}
