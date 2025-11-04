package com.sparta.customerproductsystem.service;

import com.sparta.customerproductsystem.domain.entity.Users;
import com.sparta.customerproductsystem.domain.role.UserRole;
import com.sparta.customerproductsystem.dto.LoginRequest;
import com.sparta.customerproductsystem.dto.LoginResponse;
import com.sparta.customerproductsystem.dto.SignUpRequest;
import com.sparta.customerproductsystem.dto.SignUpResponse;
import com.sparta.customerproductsystem.jwt.JwtUtils;
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
    private final JwtUtils jwtUtils;

    public SignUpResponse saveUsers(@Valid SignUpRequest request) {
        if (usersDetailRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("존재하는 이메일입니다");
        }
        Users users = new Users(request.getEmail(), passwordEncoder.encode(request.getPassword()),
                request.getName(), UserRole.USER);
        usersDetailRepository.save(users);

        return new SignUpResponse(users);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Users users = usersDetailRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) { //앞 : 입력받은 이메일, 뒤 : 인코딩된 비밀번호
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다");
        }

        String userRole = users.getRole().toString();
        String accessToken = jwtUtils.generateAccessToken(users.getId(), users.getEmail(), users.getName(), userRole);
        String refreshToken = jwtUtils.generateRefreshToken(users.getId(), users.getEmail(), users.getName(), userRole);

        return new LoginResponse(accessToken, refreshToken);
    }
}
