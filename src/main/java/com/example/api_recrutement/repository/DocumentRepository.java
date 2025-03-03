package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findById(Long id);
}
