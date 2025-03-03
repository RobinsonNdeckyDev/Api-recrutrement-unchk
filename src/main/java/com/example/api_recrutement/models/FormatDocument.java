package com.example.api_recrutement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "formats-documents")
@Data
@NoArgsConstructor
public class FormatDocument extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nomFormat; // Exemple : PDF, PNG

    @Column
    private String description;

    @Column
    private String etat;

}
