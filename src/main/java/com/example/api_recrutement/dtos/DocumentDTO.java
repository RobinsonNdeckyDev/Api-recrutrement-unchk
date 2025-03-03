package com.example.api_recrutement.dtos;

import lombok.Data;

@Data
public class DocumentDTO {
    private String titre;
    private String description;
    private Long typeDocumentId;
    private Long userId;
}
