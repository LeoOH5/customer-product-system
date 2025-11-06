package com.sparta.customerproductsystem.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchUserUpdateRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Size(min = 2, message = "이름은 최소 2글자 이상입니다.")
    private String name;
}
