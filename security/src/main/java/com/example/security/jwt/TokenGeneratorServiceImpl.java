package com.example.security.jwt;


import com.example.security.data.SecureUser;
import com.example.security.data.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenGeneratorServiceImpl implements TokenGeneratorService {
    @Autowired
    private JwtEncoder accessTokenEncoder;

    @Value("${jwt.access.duration}")
    private int accessTokenExpiryInMinutes;

    @Value("${jwt.refresh.duration}")
    private int refreshTokenExpiryInDays;

    @Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    private JwtEncoder refreshTokenEncoder;

    @Override
    public TokenResponse createToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof SecureUser secureUser)) {
            throw new BadCredentialsException(
                    MessageFormat.format("principal {0} is not of User type", authentication.getPrincipal().getClass())
            );
        }

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setUserId(secureUser.getAppUser().getId());
        tokenResponse.setAccess(createAccessToken(authentication));

        String refreshToken;
        if (authentication.getCredentials() instanceof Jwt jwt) {
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long daysUntilExpired = duration.toDays();
            if (daysUntilExpired < 7) {
                refreshToken = createRefreshToken(authentication);
            } else {
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(authentication);
        }
        tokenResponse.setRefresh(refreshToken);

        return tokenResponse;
    }


    public String createAccessToken(Authentication authentication) {
        SecureUser secureUser = (SecureUser) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("AppName")
                .issuedAt(now)
                .expiresAt(now.plus(accessTokenExpiryInMinutes, ChronoUnit.MINUTES))
                .subject(secureUser.getAppUser().getId())
                .build();

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }


    public String createRefreshToken(Authentication authentication) {
        SecureUser secureUser = (SecureUser) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("AppName")
                .issuedAt(now)
                .expiresAt(now.plus(refreshTokenExpiryInDays, ChronoUnit.DAYS))
                .subject(secureUser.getAppUser().getId())
                .build();

        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
