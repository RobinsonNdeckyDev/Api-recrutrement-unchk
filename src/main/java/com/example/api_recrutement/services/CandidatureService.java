package com.example.api_recrutement.services;

import com.example.api_recrutement.models.*;
import com.example.api_recrutement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidatureService {
    private final DocumentRepository documentRepository;
    private final CandidatureRepository candidatureRepository;
    private final UserRepository userRepository;
    private final AnnonceRepository annonceRepository;

    public CandidatureService(
            DocumentRepository documentRepository,
            CandidatureRepository candidatureRepository,
            UserRepository userRepository,
            AnnonceRepository annonceRepository
    ) {
        this.documentRepository = documentRepository;
        this.candidatureRepository = candidatureRepository;
        this.userRepository = userRepository;
        this.annonceRepository = annonceRepository;
    }

    public List<Candidature> getAllCandidatures() {
        return candidatureRepository.findAll();
    }

    public Optional<Candidature> getCandidatureById(Long id) {
        return candidatureRepository.findById(id);
    }

    /**
     * Crée une nouvelle candidature avec les documents associés.
     * @param userId L'ID de l'utilisateur.
     * @param annonceId L'ID de l'annonce.
     * @param documentIds Les IDs des documents à associer.
     * @return La candidature créée.
     */
    @Transactional
    public Candidature createCandidature(Long userId, Long annonceId, List<Long> documentIds) {
        // Vérifier si une candidature existe déjà pour cet utilisateur et cette annonce
        if (candidatureRepository.existsByUserIdAndAnnonceId(userId, annonceId)) {
            throw new RuntimeException("Une candidature existe déjà pour cet utilisateur et cette annonce");
        }

        // Récupérer l'utilisateur
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé"));

        // Récupérer l'annonce
        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));

        // Récupérer les documents
        List<Document> documents = documentRepository.findAllById(documentIds);
        if (documents.size() != documentIds.size()) {
            throw new RuntimeException("Certains documents sont introuvables");
        }

        // Créer la candidature et l'initialiser
        Candidature candidature = new Candidature();
        candidature.setUser(user);
        candidature.setAnnonce(annonce);
        candidature.setEtat(EtatCandidature.PENDING);

        // Ajout des documents via la méthode addDocument (le Set évite les doublons)
        documents.forEach(candidature::addDocument);

        // Enregistrer la candidature (Hibernate gère la table de jointure)
        return candidatureRepository.save(candidature);
    }


    /**
     * Met à jour une candidature existante.
     * @param id L'ID de la candidature à mettre à jour.
     * @param updatedCandidature Les nouvelles données de la candidature.
     * @return La candidature mise à jour.
     */
    @Transactional
    public Candidature updateCandidature(Long id, Candidature updatedCandidature) {
        Candidature existingCandidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));

        // Mise à jour de l'utilisateur si fourni
        if (updatedCandidature.getUser() != null && updatedCandidature.getUser().getId() != null) {
            User user = userRepository.findById(updatedCandidature.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User non trouvé"));
            existingCandidature.setUser(user);
        }

        // Mise à jour de l'annonce si fournie
        if (updatedCandidature.getAnnonce() != null && updatedCandidature.getAnnonce().getId() != null) {
            Annonce annonce = annonceRepository.findById(updatedCandidature.getAnnonce().getId())
                    .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
            existingCandidature.setAnnonce(annonce);
        }

        // Mise à jour de l'état si fourni
        if (updatedCandidature.getEtat() != null) {
            existingCandidature.setEtat(updatedCandidature.getEtat());
        }

        // Mise à jour des documents si la liste est fournie et non vide
        if (updatedCandidature.getDocuments() != null && !updatedCandidature.getDocuments().isEmpty()) {
            Set<Long> documentIds = updatedCandidature.getDocuments().stream()
                    .map(Document::getId)
                    .collect(Collectors.toSet());
            Set<Document> documents = new HashSet<>(documentRepository.findAllById(documentIds));
            if (documents.size() != documentIds.size()) {
                throw new RuntimeException("Certains documents sont introuvables");
            }
            // Remplacer les documents existants par le nouveau Set
            existingCandidature.setDocuments(documents);
        }

        return candidatureRepository.save(existingCandidature);
    }


    public void deleteCandidature(Long id) {
        Optional<Candidature> candidatureOptional = candidatureRepository.findById(id);
        if (!candidatureOptional.isPresent()) {
            throw new RuntimeException("Candidature non trouvée");
        }

        candidatureRepository.delete(candidatureOptional.get());
    }

}
