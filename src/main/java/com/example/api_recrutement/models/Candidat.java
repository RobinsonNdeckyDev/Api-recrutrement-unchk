package com.example.api_recrutement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "candidats")
@Data
@NoArgsConstructor
public class Candidat extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private String photoProfil;

    @Column(nullable = true)
    private String cvUrl;

    @Column(nullable = true)
    private String siteUrl;

    @Column(nullable = true)
    private String niveauEtude;

    @Column(nullable = true)
    private String domaineEtude;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
