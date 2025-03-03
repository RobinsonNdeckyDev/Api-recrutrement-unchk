package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDBReposetory extends JpaRepository<FileDB, Long> {
    // Rechercher un fichier par son nom
    Optional<FileDB> findByNom(String nom);
}
