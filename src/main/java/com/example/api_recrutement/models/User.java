package com.example.api_recrutement.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends Auditable implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String photoProfil;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters and Setters

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the authorities granted to the user
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }
}