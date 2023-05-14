package com.example.security.service;

;
import com.example.security.data.CustomException;
import com.example.security.data.LoginRequest;
import com.example.security.data.RefreshTokenRequest;
import com.example.security.data.TokenResponse;
import com.example.security.jwt.TokenGeneratorService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    private JwtAuthenticationProvider refreshTokenAuthProvider;

    @Autowired
    private BlackListingService blackListingService;

    @Override
    public TokenResponse login(LoginRequest loginRequest) throws CustomException {
        Authentication authentication = daoAuthenticationProvider
                .authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPassword()));
        return tokenGeneratorService.createToken(authentication);
    }



    @Override
    public TokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(
                new BearerTokenAuthenticationToken(refreshTokenRequest.getRefreshToken()));
        return tokenGeneratorService.createToken(authentication);
    }

    @Override
    public String logout(String token) {
        blackListingService.blackListJwt(token);
        return "Logout Successful";
    }

}
