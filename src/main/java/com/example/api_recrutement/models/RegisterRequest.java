package com.example.api_recrutement.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String prenom;
    private String nom;
    private String adresse;
    private String telephone;
    private String description;
    private String photoProfil;
    private Role role;
}
