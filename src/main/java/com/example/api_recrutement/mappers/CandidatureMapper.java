package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.CandidatureDTO;
import com.example.api_recrutement.models.Annonce;
import com.example.api_recrutement.models.Candidature;
import com.example.api_recrutement.models.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CandidatureMapper {
    CandidatureMapper INSTANCE = Mappers.getMapper(CandidatureMapper.class);

    CandidatureDTO toCandidatureDTO(Candidature candidature);

    Candidature toCandidature(CandidatureDTO candidatureDTO);

    // Add these custom mapping methods
    default List<Long> documentsToIds(List<Document> documents) {
        if (documents == null) {
            return null;
        }
        return documents.stream()
                .map(Document::getId)
                .collect(Collectors.toList());
    }

    default List<Document> idsToDocuments(List<Long> documentIds) {
        if (documentIds == null) {
            return null;
        }
        return documentIds.stream()
                .map(id -> {
                    Document doc = new Document();
                    doc.setId(id);
                    return doc;
                })
                .collect(Collectors.toList());
    }

    // Méthode de conversion pour l'annonce
    default Annonce annonceIdToAnnonce(Long annonceId) {
        if (annonceId == null) {
            return null;
        }
        Annonce annonce = new Annonce();
        annonce.setId(annonceId);
        return annonce;
    }
}
