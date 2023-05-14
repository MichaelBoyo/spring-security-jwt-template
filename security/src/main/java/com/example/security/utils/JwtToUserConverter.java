package com.example.security.utils;


import com.example.data.BaseUser;
import com.example.security.data.SecureUser;
import com.example.security.service.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    private final BaseUserRepository appBaseUserRepository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        BaseUser appUser = appBaseUserRepository.findById(jwt.getSubject()).orElseThrow(
                () -> new RuntimeException("Invalid email or password")
        );
        SecureUser secureUser = new SecureUser(appUser);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> role.getPermissions().forEach(
                permission ->
                        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(
                                role.getName() + "_" + permission.name())
                        )
        ));

        return new UsernamePasswordAuthenticationToken(secureUser, jwt, simpleGrantedAuthorities);
    }
}
