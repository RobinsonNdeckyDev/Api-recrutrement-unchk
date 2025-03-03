package com.example.api_recrutement.dtos;

import lombok.Data;

@Data
public class AnneeAcademiqueDTO {
    private String annee;
    private String description;
    private String dateDebut;
    private String dateFin;
    private Boolean etat;
}
