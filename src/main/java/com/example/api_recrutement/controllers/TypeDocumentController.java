package com.example.api_recrutement.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.api_recrutement.models.TypeDocument;
import com.example.api_recrutement.services.TypeDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.api_recrutement.dtos.TypeDocumentDTO;
import com.example.api_recrutement.mappers.TypeDocumentMapper;

import java.util.List;

// Contrôleur pour la gestion des candidats
@RestController
@RequestMapping("/api/typeDocuments")
public class TypeDocumentController {
    public final TypeDocumentService typeDocumentService;

    public TypeDocumentController(TypeDocumentService typeDocumentService) {
        this.typeDocumentService = typeDocumentService;
    }

    // Récupère tous les types de documents
    @GetMapping
    public List<TypeDocument> getAllTypesDocuments() {
        return typeDocumentService.getAllTypeDocuments();
    }

    // Récupère un type de document par son ID
    @GetMapping("/{id}")
    public ResponseEntity<TypeDocument> getTypeDocumentById(@PathVariable Long id) {
        return typeDocumentService.getTypeDocumentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent créer un type de document
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Crée un nouveau type de document
    @PostMapping
    public ResponseEntity<TypeDocument> createTypeDocument(@RequestBody TypeDocumentDTO typeDocumentDTO) {
        // Convertit typeDocumentDTO en typeDocument
        TypeDocument typeDocument = TypeDocumentMapper.INSTANCE.toTypeDocument(typeDocumentDTO);
        TypeDocument createTypeDocument = typeDocumentService.createTypeDocument(typeDocument);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTypeDocument);
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent modifier un type de document
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Met à jour un type de document
    @PutMapping("/{id}")
    public ResponseEntity<TypeDocument> updateTypeDocument(@PathVariable Long id, @RequestBody TypeDocumentDTO typeDocumentDTO) {
        // Convertit TypeDocumentDTO en typeDocument
        TypeDocument typeDocument = TypeDocumentMapper.INSTANCE.toTypeDocument(typeDocumentDTO);
        TypeDocument updateTypeDocument = typeDocumentService.updateTypeDocument(id, typeDocument);
        return ResponseEntity.ok(updateTypeDocument);
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent supprimer un type de document
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Supprime un type de document par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeDocument(@PathVariable Long id) {
        typeDocumentService.deleteTypeDocument(id);
        return ResponseEntity.noContent().build();
    }
}
