package com.example.data;

import jakarta.persistence.*;
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    public BaseUser(String mail, String password) {
        this.email = mail;
        this.password = password;
    }
}
