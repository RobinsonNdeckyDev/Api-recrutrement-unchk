package com.example.api_recrutement.controllers;

import com.example.api_recrutement.models.AnneeAcademique;
import com.example.api_recrutement.services.AnneeAcademiqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.api_recrutement.dtos.AnneeAcademiqueDTO;
import com.example.api_recrutement.mappers.AnneeAcademiqueMapper;

import java.util.List;

// Contrôleur pour la gestion des annees academiques
@RestController
@RequestMapping("/api/annees-academiques")
public class AnneeAcademiqueController {
    public final AnneeAcademiqueService anneeAcademiqueService;

    public AnneeAcademiqueController(AnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }


    // Récupère toutes les annees academiques
    @GetMapping
    public List<AnneeAcademique> getAllAnneesAcademiques() {
        return anneeAcademiqueService.getAllAnneesAcademiques();
    }

    // Récupère une annee academique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<AnneeAcademique> getAnneeAcademiqueById(@PathVariable Long id) {
        return anneeAcademiqueService.getAnneeAcademiqueById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent créer une année académique
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Crée une nouvelle annee academique
    @PostMapping
    public ResponseEntity<AnneeAcademique> createAnneeAcademique(@RequestBody AnneeAcademiqueDTO anneeAcademiqueDTO) {
        // Convertit CandidatDTO en Candidat
        AnneeAcademique anneeAcademique = AnneeAcademiqueMapper.INSTANCE.anneeAcademiqueDTOToAnneeAcademique(anneeAcademiqueDTO);
        AnneeAcademique createAnneeAcademique = anneeAcademiqueService.createAnneeAcademique(anneeAcademique);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAnneeAcademique);
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent modifier une année académique
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Modifie une annee academique
    @PutMapping("/{id}")
    public ResponseEntity<AnneeAcademique> updateAnneeAcademique(@PathVariable Long id, @RequestBody AnneeAcademiqueDTO anneeAcademiqueDTO) {
        // Convertit CandidatDTO en Candidat
        AnneeAcademiqueDTO anneeAcademique = AnneeAcademiqueMapper.INSTANCE.anneeAcademiqueToAnneeAcademiqueDTO(anneeAcademiqueDTO);
        AnneeAcademique updateAnneeAcademique = anneeAcademiqueService.updateAnneeAcademique(id, anneeAcademique);
        return ResponseEntity.ok(updateAnneeAcademique);
    }

    // Seuls les utilisateurs ayant le role 'ADMIN' peuvent supprimer une année académique
    @PreAuthorize("hasAnyRole('ADMIN')")
    // Supprime une annee academique par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnneeAcademique(@PathVariable Long id) {
        anneeAcademiqueService.deleteAnneeAcademique(id);
        return ResponseEntity.noContent().build();
    }
}
