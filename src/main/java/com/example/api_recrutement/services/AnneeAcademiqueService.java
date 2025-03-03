package com.example.api_recrutement.services;

import com.example.api_recrutement.dtos.AnneeAcademiqueDTO;
import com.example.api_recrutement.models.AnneeAcademique;
import com.example.api_recrutement.repository.AnneeAcademiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service pour la gestion des années académiques
@Service
public class AnneeAcademiqueService {
    public final AnneeAcademiqueRepository anneeAcademiqueRepository;

    public AnneeAcademiqueService(AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }

    public List<AnneeAcademique> getAllAnneesAcademiques() {
        return anneeAcademiqueRepository.findAll();
    }

    public Optional<AnneeAcademique> getAnneeAcademiqueById(Long id) {
        return anneeAcademiqueRepository.findById(id);
    }

    public AnneeAcademique createAnneeAcademique(AnneeAcademique anneeAcademique) {
        return anneeAcademiqueRepository.save(anneeAcademique);
    }

    public AnneeAcademique updateAnneeAcademique(Long id, AnneeAcademiqueDTO updatedAnneeAcademique) {
        Optional<AnneeAcademique> anneeAcademiqueOptional = anneeAcademiqueRepository.findById(id);
        if (anneeAcademiqueOptional.isPresent()) {
            AnneeAcademique anneeAcademique = anneeAcademiqueOptional.get();
            anneeAcademique.setAnnee(anneeAcademique.getAnnee());
            anneeAcademique.setDescription(updatedAnneeAcademique.getDescription());
            anneeAcademique.setDateDebut(anneeAcademique.getDateDebut());
            anneeAcademique.setDateFin(anneeAcademique.getDateFin());
            anneeAcademique.setEtat(anneeAcademique.getEtat());
            return anneeAcademiqueRepository.save(anneeAcademique);
        }
        return null;
    }

    public void deleteAnneeAcademique(Long id) {
        anneeAcademiqueRepository.deleteById(id);
    }
}
