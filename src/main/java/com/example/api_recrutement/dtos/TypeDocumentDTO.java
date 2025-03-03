package com.example.api_recrutement.dtos;

import com.example.api_recrutement.models.EtatEntiteGlobal;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TypeDocumentDTO {
    private String nomType;
    private String description;
    private Boolean estObligatoire;
    private EtatEntiteGlobal etat;
    private Long formatDocumentId;
}
