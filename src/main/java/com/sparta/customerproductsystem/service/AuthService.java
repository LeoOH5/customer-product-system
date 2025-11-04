package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import com.sparta.customerproductsystem.dto.SignUpRequest;
import com.sparta.customerproductsystem.dto.SignUpResponse;
import com.sparta.customerproductsystem.repository.UsersDetailRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersDetailRepository usersDetailRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse saveUsers(@Valid SignUpRequest request) {
        if (usersDetailRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("존재하는 이메일입니다");
        }
        Users users = new Users(request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                UserRole.CUSTOMER);
        usersDetailRepository.save(users);

        return new SignUpResponse(users);
    }
}
