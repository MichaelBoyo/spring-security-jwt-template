package com.example.data;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Role {
    @Id
    @UuidGenerator
    private String id;
    @ElementCollection
    private Set<Permission> permissions = new HashSet<>();
    private RoleName name;

    public Role(RoleName roleName) {
        this.name = roleName;
    }
}
