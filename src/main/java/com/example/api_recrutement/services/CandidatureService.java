package com.example.api_recrutement.services;

import com.example.api_recrutement.models.*;
import com.example.api_recrutement.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Candidature createCandidature(Long userId, Long annonceId, List<Long> documentIds) {
        // Vérifier si une candidature existe déjà pour cet utilisateur et cette annonce
        if (candidatureRepository.existsByUserIdAndAnnonceId(userId, annonceId)) {
            throw new RuntimeException("Une candidature existe déjà pour cet utilisateur et cette annonce");
        }

        // Récupérer le user
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

        // Créer une nouvelle candidature
        Candidature candidature = new Candidature();
        candidature.setUser(user);
        candidature.setAnnonce(annonce);
        candidature.setEtat(EtatCandidature.PENDING);

        // Sauvegarder la candidature pour obtenir l'ID
        candidature = candidatureRepository.save(candidature);

        // Associer les documents à la candidature
        for (Document document : documents) {
            if (!candidature.getDocumentIds().contains(document)) {
                candidature.getDocumentIds().add(document);
            }
        }

        return candidatureRepository.save(candidature);
    }

    public Candidature updateCandidature(Long id, Candidature candidature) {
        Candidature existingCandidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));

        // Mise à jour de l'utilisateur si fourni
        if (candidature.getUser() != null && candidature.getUser().getId() != null) {
            User user = userRepository.findById(candidature.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User non trouvé"));
            existingCandidature.setUser(user);
        }

        // Mise à jour de l'annonce si fourni
        if (candidature.getAnnonce() != null && candidature.getAnnonce().getId() != null) {
            Annonce annonce = annonceRepository.findById(candidature.getAnnonce().getId())
                    .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
            existingCandidature.setAnnonce(annonce);
        }

        // Mise à jour de l'état si fourni (permet de changer uniquement l'état)
        if (candidature.getEtat() != null) {
            existingCandidature.setEtat(candidature.getEtat());
        }

        // Mise à jour des documents si la liste est fournie et non vide
        if (candidature.getDocumentIds() != null && !candidature.getDocumentIds().isEmpty()) {
            List<Long> documentIds = candidature.getDocumentIds().stream()
                    .map(Document::getId)
                    .collect(Collectors.toList());
            List<Document> documents = documentRepository.findAllById(documentIds);
            if (documents.size() != documentIds.size()) {
                throw new RuntimeException("Certains documents sont introuvables");
            }
            existingCandidature.getDocumentIds().clear();
            existingCandidature.getDocumentIds().addAll(documents);
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
