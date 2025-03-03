package com.example.api_recrutement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "annonces")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Annonce extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String contenu;

    @Column(nullable = true)
    private String imageAnnonce;

    @Column(nullable = false)
    private String auteur;

    @Column(nullable = false)
    private LocalDate dateLimite;

    @Column(nullable = false)
    private String etat = "OUVERTE"; // Par défaut, l'annonce est ouverte

    @ManyToOne
    @JoinColumn(name = "id_annee", nullable = false)
    private AnneeAcademique anneeAcademique;
}




