package com.example.security.service;

import com.example.security.dtos.LoginRequest;
import com.example.security.dtos.RefreshTokenRequest;
import com.example.security.dtos.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest loginRequest);

    TokenResponse refresh(RefreshTokenRequest refreshTokenRequest);
}
