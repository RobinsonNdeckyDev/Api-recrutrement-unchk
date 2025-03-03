package com.example.api_recrutement.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AnnonceDTO {
    private String titre;
    private String description;
    private String contenu;
    private String imageAnnonce;
    private String auteur;
    private LocalDate dateLimite;
    private String etat;
    private Long anneeAcademiqueId;
}
