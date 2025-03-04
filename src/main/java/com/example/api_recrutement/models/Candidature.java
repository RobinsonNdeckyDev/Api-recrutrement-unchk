package com.example.api_recrutement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "candidatures")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Candidature extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EtatCandidature etat;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "annonceId", nullable = false)
    private Annonce annonce;

    @ManyToMany
    @JoinTable(
            name = "candidature_documents",
            joinColumns = @JoinColumn(name = "candidature_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"candidature_id", "document_id"})
    )
    private Set<Document> documents = new HashSet<>();


    // Méthode utilitaire pour ajouter un document à la candidature (évite les doublons en mémoire)
    public void addDocument(Document document) {
        this.documents.add(document);
    }

}


