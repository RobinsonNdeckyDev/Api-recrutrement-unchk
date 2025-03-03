package com.example.api_recrutement.dtos;

import com.example.api_recrutement.models.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String prenom;
    private String nom;
    private String telephone;
    private String adresse;
    private String photoProfil;
    private String description;
    private Role role;
}
