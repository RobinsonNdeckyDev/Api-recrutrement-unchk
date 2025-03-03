package com.example.api_recrutement.services;

import com.example.api_recrutement.models.Candidat;
import com.example.api_recrutement.models.Role;
import com.example.api_recrutement.models.User;
import com.example.api_recrutement.repository.CandidatRepository;
import com.example.api_recrutement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service pour la gestion des candidats
@Service
public class CandidatService {
    public final CandidatRepository candidatRepository;
    private final UserRepository userRepository;  // Ajout du UserRepository

    public CandidatService(CandidatRepository candidatRepository, UserRepository userRepository) {
        this.candidatRepository = candidatRepository;
        this.userRepository = userRepository;
    }

    public List<Candidat> getAllCandidats() {
        return candidatRepository.findAll();
    }

    public Optional<Candidat> getCandidatById(Long id) {
        return candidatRepository.findById(id);
    }

    @Transactional
    public Candidat createCandidat(Candidat candidat) {
        // Créer d'abord l'utilisateur
        User user = new User();
        user.setEmail(candidat.getEmail());
        user.setPassword(candidat.getPassword());
        user.setRole(Role.CANDIDAT);
        user.setNom(candidat.getNom());
        user.setPrenom(candidat.getPrenom());
        user.setTelephone(candidat.getTelephone());
        user.setAdresse(candidat.getAdresse());
        user.setDescription(candidat.getDescription());
        user.setPhotoProfil(candidat.getPhotoProfil());
        User savedUser = userRepository.save(user);

        // Lier l'utilisateur au candidat
        candidat.setUser(savedUser);
        return candidatRepository.save(candidat);
    }

//    public Candidat createCandidat(Candidat candidat) {
//        return candidatRepository.save(candidat);
//    }

    @Transactional
    public Candidat updateCandidat(Long id, Candidat candidatDetails) {
        return candidatRepository.findById(id).map(candidat -> {
            // Mise à jour des informations spécifiques au candidat
            candidat.setCvUrl(candidatDetails.getCvUrl());
            candidat.setSiteUrl(candidatDetails.getSiteUrl());
            candidat.setNiveauEtude(candidatDetails.getNiveauEtude());
            candidat.setDomaineEtude(candidatDetails.getDomaineEtude());

            // Mise à jour des informations communes avec l'utilisateur
            candidat.setNom(candidatDetails.getNom());
            candidat.setPrenom(candidatDetails.getPrenom());
            candidat.setTelephone(candidatDetails.getTelephone());
            candidat.setAdresse(candidatDetails.getAdresse());
            candidat.setDescription(candidatDetails.getDescription());
            candidat.setPhotoProfil(candidatDetails.getPhotoProfil());

            // Mise à jour de l'utilisateur associé
            User user = candidat.getUser();
            if (user != null) {
                user.setNom(candidatDetails.getNom());
                user.setPrenom(candidatDetails.getPrenom());
                user.setTelephone(candidatDetails.getTelephone());
                user.setAdresse(candidatDetails.getAdresse());
                user.setDescription(candidatDetails.getDescription());
                user.setPhotoProfil(candidatDetails.getPhotoProfil());
                userRepository.save(user);
            }

            return candidatRepository.save(candidat);
        }).orElseThrow(() -> new RuntimeException("Candidat non existant"));
    }

    @Transactional
    public void deleteCandidat(Long id) {
        Candidat candidat = candidatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        // Supprimer d'abord le candidat
        candidatRepository.deleteById(id);

        // Puis supprimer l'utilisateur associé
        if (candidat.getUser() != null) {
            userRepository.deleteById(candidat.getUser().getId());
        }
    }

//    public void deleteCandidat(Long id) {
//        candidatRepository.deleteById(id);
//    }
}
