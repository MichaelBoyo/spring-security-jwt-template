package com.example.security.service;


import com.example.security.data.CustomException;
import com.example.security.data.LoginRequest;
import com.example.security.data.RefreshTokenRequest;
import com.example.security.data.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest loginRequest) throws CustomException;

    TokenResponse refresh(RefreshTokenRequest refreshTokenRequest);

    String logout(String token);
}
