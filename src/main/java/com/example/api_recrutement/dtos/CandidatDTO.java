package com.example.api_recrutement.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CandidatDTO extends UserDTO {
    private String cvUrl;
    private String siteUrl;
    private String niveauEtude;
    private String domaineEtude;
}
