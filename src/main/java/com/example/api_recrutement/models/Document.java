package com.example.api_recrutement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre; // Titre du document

    @Column(nullable = false)
    private String description; // Description du document

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;
    // Utilisation de BLOB pour stocker les données binaires

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "typeDocument_id", nullable = false)
    private TypeDocument typeDocument;

    // Constructeur pour faciliter la création de documents
    public Document(String titre, String description, byte[] data, TypeDocument typeDocument) {
        this.titre = titre;
        this.description = description;
        this.data = data;
        this.typeDocument = typeDocument;
    }

}
