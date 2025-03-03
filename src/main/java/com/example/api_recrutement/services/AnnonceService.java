package com.example.api_recrutement.services;

import com.example.api_recrutement.models.Annonce;
import com.example.api_recrutement.repository.AnnonceRepository;
import com.example.api_recrutement.repository.AnneeAcademiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {
    private final AnnonceRepository annonceRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    public AnnonceService(AnnonceRepository annonceRepository, AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.annonceRepository = annonceRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }

    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    public Annonce createAnnonce(Annonce annonce) {
        // Assurez-vous que l'année académique existe
        Long anneeAcademiqueId = annonce.getAnneeAcademique().getId();
        return anneeAcademiqueRepository.findById(anneeAcademiqueId)
                .map(anneeAcademique -> {
                    annonce.setAnneeAcademique(anneeAcademique);
                    return annonceRepository.save(annonce);
                })
                .orElseThrow(() -> new RuntimeException("Année académique non existante"));
    }

    public Annonce updateAnnonce(Annonce annonceDetails) {
        return annonceRepository.findById(annonceDetails.getId()).map(annonce -> {
            annonce.setTitre(annonceDetails.getTitre());
            annonce.setDescription(annonceDetails.getDescription());
            annonce.setImageAnnonce(annonceDetails.getImageAnnonce());
            annonce.setContenu(annonceDetails.getContenu());
            annonce.setAuteur(annonceDetails.getAuteur());
            annonce.setEtat(annonceDetails.getEtat());
            annonce.setAnneeAcademique(annonceDetails.getAnneeAcademique());
            annonce.setDateLimite(annonceDetails.getDateLimite());
            return annonceRepository.save(annonce);
        }).orElseThrow(() -> new RuntimeException("Annonce non existante"));
    }

    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }
}
