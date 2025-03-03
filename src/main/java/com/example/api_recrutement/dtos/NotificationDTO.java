package com.example.api_recrutement.dtos;

import lombok.Data;

@Data
public class NotificationDTO {
    private String titre;
    private String description;
    private String contenu;
    private Long adminId;
    private Long candidatId;
    private Long candidatureId;
}
