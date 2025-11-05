package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.RefreshToken;
import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import com.sparta.customerproductsystem.dto.authdto.*;
import com.sparta.customerproductsystem.exception.BusinessException;
import com.sparta.customerproductsystem.exception.ErrorCode;
import com.sparta.customerproductsystem.repository.RefreshTokenRepository;
import com.sparta.customerproductsystem.repository.UserRepository;
import com.sparta.customerproductsystem.security.UserPrincipal;
import com.sparta.customerproductsystem.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public SignUpResponse saveUsers(@Valid SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw BusinessException.of(ErrorCode.INVALID_EMAIL_FORMAT);
        }
        Users users = new Users(request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                UserRole.CUSTOMER);
        userRepository.save(users);

        return new SignUpResponse(users);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        Users users = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> BusinessException.of(ErrorCode.INVALID_EMAIL_FORMAT));

        if (!passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
            throw BusinessException.of(ErrorCode.IVALID_PASSWORD);
        }

        String userRole = users.getRole().toString();
        String accessToken = jwtUtils.generateAccessToken(users.getId(), users.getEmail(), users.getName(), userRole);
        String refreshToken = jwtUtils.generateRefreshToken(users.getId(), users.getEmail(), users.getName(), userRole);

        refreshTokenRepository.save(new RefreshToken(refreshToken, users));
        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public AdminCreateUserResponse adminUserSave(AdminCreateUserRequest adminCreateUserRequest) {
        if (userRepository.existsByEmail(adminCreateUserRequest.getEmail())) {
            throw BusinessException.of(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        Users user = new Users(adminCreateUserRequest.getEmail(),
                passwordEncoder.encode(adminCreateUserRequest.getPassword()),
                adminCreateUserRequest.getName(),
                adminCreateUserRequest.getRole());
        userRepository.save(user);

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

    @Transactional
    public void logout(UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();

        refreshTokenRepository.deleteByUserId(userId);
    }
}
