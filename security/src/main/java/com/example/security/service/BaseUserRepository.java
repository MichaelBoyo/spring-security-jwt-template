package com.example.security.service;

import com.example.data.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserRepository extends JpaRepository<BaseUser, String> {
    Optional<BaseUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
