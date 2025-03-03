package com.example.api_recrutement.controllers;

import com.example.api_recrutement.models.Candidat;
import com.example.api_recrutement.services.CandidatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.api_recrutement.dtos.CandidatDTO;
import com.example.api_recrutement.mappers.CandidatMapper;

import java.util.List;

// Contrôleur pour la gestion des candidats
@RestController
@RequestMapping("/api/candidats")
public class CandidatController {
    public final CandidatService candidatService;

    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;
    }

    // Récupère tous les candidats
    @GetMapping
    public List<Candidat> getAllCandidats() {
        return candidatService.getAllCandidats();
    }

    // Récupère un candidat par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Candidat> getCandidatById(@PathVariable Long id) {
        return candidatService.getCandidatById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crée un nouveau candidat
    @PostMapping
    public ResponseEntity<Candidat> createCandidat(@RequestBody CandidatDTO candidatDTO) {
        // Convertit CandidatDTO en Candidat
        Candidat candidat = CandidatMapper.INSTANCE.toCandidat(candidatDTO);
        Candidat createdCandidat = candidatService.createCandidat(candidat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidat);
    }

    // Met à jour un candidat existant
    @PutMapping("/{id}")
    public ResponseEntity<Candidat> updateCandidat(@PathVariable Long id, @RequestBody CandidatDTO candidatDTO) {
        // Convertit CandidatDTO en Candidat
        Candidat candidat = CandidatMapper.INSTANCE.toCandidat(candidatDTO);
        Candidat updatedCandidat = candidatService.updateCandidat(id, candidat);
        return ResponseEntity.ok(updatedCandidat);
    }

    // Supprime un candidat par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidat(@PathVariable Long id) {
        candidatService.deleteCandidat(id);
        return ResponseEntity.noContent().build();
    }
}