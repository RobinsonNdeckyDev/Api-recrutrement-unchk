package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long> {
    Optional<TypeDocument> findById(Long id);
}
