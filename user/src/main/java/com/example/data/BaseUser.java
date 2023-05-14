package com.example.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class BaseUser {
    @UuidGenerator
    @Id
    private String id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    private boolean isEnabled = true;
    @OneToMany
    private Set<Role> roles = new HashSet<>();

    public BaseUser(String mail, String password) {
        this.email = mail;
        this.password = password;
    }
}
