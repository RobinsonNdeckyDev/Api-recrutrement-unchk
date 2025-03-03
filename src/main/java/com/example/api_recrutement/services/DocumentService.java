package com.example.api_recrutement.services;

import com.example.api_recrutement.models.User;
import com.example.api_recrutement.models.Candidature;
import com.example.api_recrutement.models.Document;
import com.example.api_recrutement.models.TypeDocument;
import com.example.api_recrutement.repository.CandidatureRepository;
import com.example.api_recrutement.repository.DocumentRepository;
import com.example.api_recrutement.repository.TypeDocumentRepository;
import com.example.api_recrutement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final CandidatureRepository candidatureRepository;
    private final TypeDocumentRepository typeDocumentRepository;
    private final UserRepository userRepository;

    public DocumentService(
            DocumentRepository documentRepository,
            CandidatureRepository candidatureRepository,
            UserRepository userRepository,
            TypeDocumentRepository typeDocumentRepository
    ) {
        this.documentRepository = documentRepository;
        this.candidatureRepository = candidatureRepository;
        this.typeDocumentRepository = typeDocumentRepository;
        this.userRepository = userRepository;
    }

    public Optional<Candidature> getCandidatureById(Long id) {
        return candidatureRepository.findById(id);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public Document createDocument(MultipartFile file, String titre, String description, Long typeDocumentId, Long userId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }

        // Récupérer le user par son ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé"));

        // Récupérer le type de document par son ID
        TypeDocument typeDocument = typeDocumentRepository.findById(typeDocumentId)
                .orElseThrow(() -> new RuntimeException("Type de document non trouvé"));

        byte[] fileData = file.getBytes();
        Document document = new Document(titre, description, fileData, typeDocument);
        document.setUser(user);

        return documentRepository.save(document);
    }

    public Document updateDocument(Long id, Document documentDetails) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé avec l'id: " + id));

        document.setTitre(documentDetails.getTitre());
        document.setDescription(documentDetails.getDescription());
        document.setTypeDocument(documentDetails.getTypeDocument());
        document.setUser(documentDetails.getUser());
        if (documentDetails.getData() != null) {
            document.setData(documentDetails.getData());
        }

        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

}
