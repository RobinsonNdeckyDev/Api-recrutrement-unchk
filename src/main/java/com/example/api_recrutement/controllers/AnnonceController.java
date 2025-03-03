package com.example.api_recrutement.controllers;

import com.example.api_recrutement.models.Annonce;
import com.example.api_recrutement.services.AnnonceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.api_recrutement.dtos.AnnonceDTO;
import com.example.api_recrutement.mappers.AnnonceMapper;

import java.util.List;

// Contrôleur pour la gestion des annonces
@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    public final AnnonceService annonceService;

    public AnnonceController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    // Récupère toutes les annonces
    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceService.getAllAnnonces();
    }

    // Récupère un candidat par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        return annonceService.getAnnonceById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent créer une annonce
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Crée une nouvelle annonce
    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(@RequestBody AnnonceDTO annonceDTO) {
        // Convertit AnnonceDTO en annonce
        Annonce annonce = AnnonceMapper.INSTANCE.toAnnonce(annonceDTO);
        Annonce createAnnonce = annonceService.createAnnonce(annonce);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAnnonce);
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent modifier une annonce
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Modifie une annonce existante
    @PutMapping("/{id}")
    public ResponseEntity<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody AnnonceDTO annonceDTO) {
        return annonceService.getAnnonceById(id)
               .map(annonce -> {
                    // Convertit AnnonceDTO en annonce
                    Annonce updatedAnnonce = AnnonceMapper.INSTANCE.toAnnonce(annonceDTO);
                    updatedAnnonce.setId(id);
                    Annonce updatedAnnonceSaved = annonceService.updateAnnonce(updatedAnnonce);
                    return ResponseEntity.ok(updatedAnnonceSaved);
                })
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent supprimer une annonce
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Supprime une annonce par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }
}
