package com.example.api_recrutement.services;

import com.example.api_recrutement.models.FormatDocument;
import com.example.api_recrutement.repository.FormatDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormatDocumentService {
    private final FormatDocumentRepository formatDocumentRepository;

    public FormatDocumentService(FormatDocumentRepository formatDocumentRepository) {
        this.formatDocumentRepository = formatDocumentRepository;
    }

    public List<FormatDocument> getAllFormatDocuments() {
        return formatDocumentRepository.findAll();
    }

    public Optional<FormatDocument> getFormatDocumentById(Long id) {
        return formatDocumentRepository.findById(id);
    }

    public FormatDocument createFormatDocument(FormatDocument formatDocument) {
        return formatDocumentRepository.save(formatDocument);
    }

    public FormatDocument updateFormatDocument(Long id, FormatDocument formatDocumentDetails) {
        return formatDocumentRepository.findById(id).map(formatDocument -> {
            formatDocument.setNomFormat(formatDocumentDetails.getNomFormat());
            formatDocument.setDescription(formatDocumentDetails.getDescription());
            formatDocument.setEtat(formatDocumentDetails.getEtat());
            return formatDocumentRepository.save(formatDocument);
        }).orElseThrow(() -> new RuntimeException("format document non existant"));
    }

    public void deleteFormatDocument(Long id) {
        formatDocumentRepository.deleteById(id);
    }
}
