package com.example.api_recrutement.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Table(name = "annees_academiques")
@Data
@NoArgsConstructor
@Getter
@Setter
public class AnneeAcademique extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String annee; // Format attendu : "2024-2025"

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private boolean etat; // boolean

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;

    public boolean getEtat() {
        return etat;
    }
}
