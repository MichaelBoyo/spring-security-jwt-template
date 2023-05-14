package com.example.security.service;

import com.example.security.dtos.LoginRequest;
import com.example.security.dtos.RefreshTokenRequest;
import com.example.security.dtos.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public TokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }
}
