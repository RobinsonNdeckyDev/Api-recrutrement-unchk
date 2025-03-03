package com.example.api_recrutement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "types_documents")
@Data
@NoArgsConstructor
public class TypeDocument extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomType; // Exemple : CV, Lettre de Motivation

    @Column
    private String description;

    @Column(nullable = false)
    private boolean estObligatoire; // Est-ce que ce type de document est obligatoire ?

    @Column
    private EtatEntiteGlobal etat;

    @ManyToOne
    @JoinColumn(name = "formatDocumentId", nullable = false)
    private FormatDocument formatDocument;

    public boolean getEstObligatoire() {
        return estObligatoire;
    }
}
