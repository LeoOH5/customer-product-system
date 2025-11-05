package com.sparta.customerproductsystem.dto;

import com.sparta.customerproductsystem.domain.role.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminCreateUserRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 30, message = "이름은 최대 30자까지 입력 가능합니다.")
    private String name;

    @NotNull(message = "역할(role)은 필수 입력값입니다. ADMIN 또는 CUSTOMER 중 하나를 입력해주세요.")
    private UserRole role;

}
