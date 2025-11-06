package com.sparta.customerproductsystem.controller;

import com.sparta.customerproductsystem.dto.userdto.*;
import com.sparta.customerproductsystem.exception.CommonResponse;
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
    public ResponseEntity<CommonResponse<List<GetUserListResponse>>> getUserList(
            @RequestParam(required = false) String keyword
    ) {

        List<GetUserListResponse> result = userService.userList(keyword);

        CommonResponse<List<GetUserListResponse>> body =
                CommonResponse.<List<GetUserListResponse>>builder()
                        .data(result)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 리스트 페이징 조회
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<CommonResponse<GetUserPageResponse>> getUserListPage(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {

        GetUserPageResponse result = userService.getUserListPage(keyword, pageable);

        CommonResponse<GetUserPageResponse> body =
                CommonResponse.<GetUserPageResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 상세 정보 조회
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')") // 본인 또는 관리자 권한만 상세정보 조회 가능
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<GetUserDetailResponse>> getUserDetail(@PathVariable Long id){
        GetUserDetailResponse result = userService.findUserById(id);

        CommonResponse<GetUserDetailResponse> body =
                CommonResponse.<GetUserDetailResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 정보 수정 (일반)
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<PatchUserUpdateResponse>> patchUserUpdate(
            @PathVariable Long id, @RequestBody PatchUserUpdateRequest updateRequest){

        PatchUserUpdateResponse result = userService.updateUser(id, updateRequest);

        CommonResponse<PatchUserUpdateResponse> body =
                CommonResponse.<PatchUserUpdateResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 정보 수정 (관리자 접근 페이지)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admins/{id}")
    public ResponseEntity<CommonResponse<PatchUserUpdateByAdminResponse>> patchUserUpdateByAdmin(
            @PathVariable Long id, @RequestBody PatchUserUpdateByAdminRequest updateRequest) {


        PatchUserUpdateByAdminResponse result = userService.updateUserByAdmin(id, updateRequest);

        CommonResponse<PatchUserUpdateByAdminResponse> body =
                CommonResponse.<PatchUserUpdateByAdminResponse>builder()
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}
