package com.example.security.jwt;


import com.example.security.data.TokenResponse;
import org.springframework.security.core.Authentication;

public interface TokenGeneratorService {
    TokenResponse createToken(Authentication authentication);

}