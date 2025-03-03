package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.DocumentDTO;
import com.example.api_recrutement.models.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDTO toDocumentDTO(Document document);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "typeDocumentId", target = "typeDocument.id")
    Document toDocument(DocumentDTO documentDTO);
}
