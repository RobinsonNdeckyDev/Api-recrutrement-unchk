package com.example.api_recrutement.controllers;

import com.example.api_recrutement.models.Document;
import com.example.api_recrutement.models.User;
import com.example.api_recrutement.models.TypeDocument;
import com.example.api_recrutement.dtos.DocumentDTO;
import com.example.api_recrutement.services.DocumentService;
import com.example.api_recrutement.services.TypeDocumentService;
import com.example.api_recrutement.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final UserService userService;
    private final DocumentService documentService;
    private final TypeDocumentService typeDocumentService;

    public DocumentController(
            DocumentService documentService,
            UserService userService,
            TypeDocumentService typeDocumentService
    ) {
        this.documentService = documentService;
        this.typeDocumentService = typeDocumentService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getTitre() + "\"")
                .body(document.getData());
    }

    @PostMapping
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titre") String titre,
            @RequestParam("description") String description,
            @RequestParam("typeDocumentId") Long typeDocumentId,
            @RequestParam("userId") Long userId) {
        try {
            TypeDocument typeDocument = typeDocumentService.getTypeDocumentById(typeDocumentId)
                    .orElseThrow(() -> new RuntimeException("Type de document non trouvé"));

            Document document = documentService.createDocument(file, titre, description, typeDocumentId, userId);
            return ResponseEntity.ok(document);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'upload du document: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDAT') or hasRole('ADMIN')")
    public ResponseEntity<Document> updateDocument(
            @PathVariable Long id,
            @ModelAttribute DocumentDTO documentDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Récupérer les entités en gérant les Optional
            Document existingDocument = documentService.getDocumentById(id)
                    .orElseThrow(() -> new RuntimeException("Document non trouvé"));

            TypeDocument typeDocument = typeDocumentService.getTypeDocumentById(documentDTO.getTypeDocumentId())
                    .orElseThrow(() -> new RuntimeException("Type de document non trouvé"));

            User user = userService.getUserById(documentDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Mettre à jour le document
            existingDocument.setTitre(documentDTO.getTitre());
            existingDocument.setDescription(documentDTO.getDescription());
            existingDocument.setTypeDocument(typeDocument);
            existingDocument.setUser(user);

            if (file != null && !file.isEmpty()) {
                existingDocument.setData(file.getBytes());
            }

            Document updatedDocument = documentService.updateDocument(id, existingDocument);
            return ResponseEntity.ok(updatedDocument);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }

}
