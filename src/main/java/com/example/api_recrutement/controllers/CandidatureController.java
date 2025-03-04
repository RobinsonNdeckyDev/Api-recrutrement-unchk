package com.example.api_recrutement.controllers;

import com.example.api_recrutement.models.Candidature;
import com.example.api_recrutement.services.CandidatureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.api_recrutement.dtos.CandidatureDTO;
import com.example.api_recrutement.mappers.CandidatureMapper;

import java.util.List;

// Contrôleur pour la gestion des candidatures
@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {
    public final CandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    // Récupère toutes les candiddatures
    @GetMapping
    public List<Candidature> getAllCandidatures() {
        return candidatureService.getAllCandidatures();
    }

    // Récupère une candidature par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable Long id) {
        return candidatureService.getCandidatureById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crée une nouvelle candidature
    @PostMapping
    public ResponseEntity<Candidature> createCandidature(@Valid @RequestBody CandidatureDTO candidatureDTO) {
        Candidature candidature = candidatureService.createCandidature(
                candidatureDTO.getUserId(),
                candidatureDTO.getAnnonceId(),
                candidatureDTO.getDocumentIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(candidature);
    }


    // Modifie une candidature existante
    @PutMapping("/{id}")
    public ResponseEntity<Candidature> updateCandidature(@PathVariable Long id, @RequestBody CandidatureDTO candidatureDTO) {
        Candidature candidature = CandidatureMapper.INSTANCE.toCandidature(candidatureDTO);
        Candidature updateCandidature = candidatureService.updateCandidature(id, candidature);
        return ResponseEntity.ok(updateCandidature);
    }

    // Supprime une candidature
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        candidatureService.deleteCandidature(id);
        return ResponseEntity.noContent().build();
    }

}
