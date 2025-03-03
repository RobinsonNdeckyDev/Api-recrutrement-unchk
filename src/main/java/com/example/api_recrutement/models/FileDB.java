package com.example.api_recrutement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String type;
    private String chemin;
    private String url;

    public FileDB(String nom, String type, String chemin, String url) {
        this.nom = nom;
        this.type = type;
        this.chemin = chemin;
        this.url = url;
    }

    // Getters et Setters explicites si Lombok ne fonctionne pas
    public String getChemin() {
        return this.chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
