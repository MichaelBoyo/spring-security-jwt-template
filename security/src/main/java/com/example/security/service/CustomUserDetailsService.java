package com.example.security.service;


import com.example.data.BaseUser;
import com.example.data.Permission;
import com.example.data.Role;
import com.example.data.RoleName;
import com.example.security.data.SecureUser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final PasswordEncoder passwordEncoder;

    private final BaseUserRepository baseUserRepository;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @PostConstruct
    void initUsers() {
        Role role = new Role(RoleName.SUPER_ADMIN);
        role.getPermissions().addAll(Set.of(Permission.READ, Permission.WRITE));
        BaseUser baseUser = new BaseUser("superadmin@mail.com", passwordEncoder.encode("superadmin"));
        baseUser.getRoles().add(role);
        if (!baseUserRepository.existsByEmail(baseUser.getEmail())) {
            baseUserRepository.save(baseUser);
        }
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            BaseUser enumUser = baseUserRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("invalid email or password"));
            return new SecureUser(enumUser);
        };
    }
}
