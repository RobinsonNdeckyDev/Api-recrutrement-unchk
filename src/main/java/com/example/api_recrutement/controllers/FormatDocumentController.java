package com.example.api_recrutement.controllers;

import com.example.api_recrutement.models.FormatDocument;
import com.example.api_recrutement.services.FormatDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.api_recrutement.dtos.FormatDocumentDTO;
import com.example.api_recrutement.mappers.FormatDocumentMapper;

import java.util.List;

// Contrôleur pour la gestion des candidats
@RestController
@RequestMapping("/api/formatDocuments")
public class FormatDocumentController {
    public final FormatDocumentService formatDocumentService;

    public FormatDocumentController(FormatDocumentService formatDocumentService) {
        this.formatDocumentService = formatDocumentService;
    }

    // Récupère tous les formats de documen
    @GetMapping
    public List<FormatDocument> getAllFormatDocuments() {
        return formatDocumentService.getAllFormatDocuments();
    }

    // Récupère un format de document par son ID
    @GetMapping("/{id}")
    public ResponseEntity<FormatDocument> getFormatDocumentById(@PathVariable Long id) {
        return formatDocumentService.getFormatDocumentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent créer un format de document
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Crée un nouveau format de document
    @PostMapping
    public ResponseEntity<FormatDocument> createFormatDocument(@RequestBody FormatDocumentDTO formatDocumentDTO) {
        // Convertit FormatDocumentDTO en FormatDocument
        FormatDocument formatDocument = FormatDocumentMapper.INSTANCE.toFormatDocument(formatDocumentDTO);
        FormatDocument createFormatDocument = formatDocumentService.createFormatDocument(formatDocument);
        return ResponseEntity.status(HttpStatus.CREATED).body(createFormatDocument);
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent mettre à jour un format de document
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Met à jour un Format de document
    @PutMapping("/{id}")
    public ResponseEntity<FormatDocument> updateFormatDocument(@PathVariable Long id, @RequestBody FormatDocumentDTO formatDocumentDTO) {
        // Convertit FormatDocumentDTO en formatDocument
        FormatDocument formatDocument = FormatDocumentMapper.INSTANCE.toFormatDocument(formatDocumentDTO);
        FormatDocument updateFormatDocument = formatDocumentService.updateFormatDocument(id, formatDocument);
        return ResponseEntity.ok(updateFormatDocument);
    }

    // Seuls les utilisateurs ayant un role 'ADMIN' peuvent supprimer un format de document
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Supprime un format de document par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormatDocument(@PathVariable Long id) {
        formatDocumentService.deleteFormatDocument(id);
        return ResponseEntity.noContent().build();
    }

}
