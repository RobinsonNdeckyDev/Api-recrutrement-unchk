package com.example.api_recrutement.repository;

import com.example.api_recrutement.models.FormatDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormatDocumentRepository extends JpaRepository<FormatDocument, Long> {
    Optional<FormatDocument>findFormatDocumentById(Long id);
}
