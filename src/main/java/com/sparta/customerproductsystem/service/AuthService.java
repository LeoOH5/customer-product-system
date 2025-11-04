package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import com.sparta.customerproductsystem.dto.*;
import com.sparta.customerproductsystem.utils.JwtUtils;
import com.sparta.customerproductsystem.repository.UsersDetailRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersDetailRepository usersDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public SignUpResponse saveUsers(SignUpRequest signUpRequest) {
        if (usersDetailRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("존재하는 이메일입니다");
        }
        Users user = new Users(signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                UserRole.CUSTOMER);
        usersDetailRepository.save(user);

        return new SignUpResponse(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Users user = usersDetailRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다");
        }

        String userRole = user.getRole().toString();
        String accessToken = jwtUtils.generateAccessToken(user.getId(), user.getEmail(), user.getName(), userRole);
        String refreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getEmail(), user.getName(), userRole);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public AdminCreateUserResponse adminUserSave(AdminCreateUserRequest adminCreateUserRequest) {
        if (usersDetailRepository.existsByEmail(adminCreateUserRequest.getEmail())) {
            throw new IllegalArgumentException("존재하는 이메일입니다");
        }

        Users user = new Users(adminCreateUserRequest.getEmail(),
                passwordEncoder.encode(adminCreateUserRequest.getPassword()),
                adminCreateUserRequest.getName(),
                adminCreateUserRequest.getRole());
        usersDetailRepository.save(user);

        return new AdminCreateUserResponse(user);
    }

    public RefreshResponse refresh(RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        Long id = jwtUtils.getUserId(refreshToken);
        String email = jwtUtils.getEmail(refreshToken);
        String name = jwtUtils.getName(refreshToken);
        String role = jwtUtils.getRole(refreshToken);

        String accessToken = jwtUtils.generateAccessToken(id, email, name, role);
        String refreshTokenToken = jwtUtils.generateRefreshToken(id, email,name, role);

        return new RefreshResponse(accessToken, refreshTokenToken);
    }
}
