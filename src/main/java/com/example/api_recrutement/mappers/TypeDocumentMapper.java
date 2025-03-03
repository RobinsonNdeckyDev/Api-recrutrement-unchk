package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.TypeDocumentDTO;
import com.example.api_recrutement.models.TypeDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TypeDocumentMapper {
    TypeDocumentMapper INSTANCE = Mappers.getMapper(TypeDocumentMapper.class);

//    @Mapping(source = "formatDocument.id", target = "formatDocumentId")
    TypeDocumentDTO toTypeDocumentDTO(TypeDocument typeDocument);

    @Mapping(target = "formatDocument.id", source = "formatDocumentId")
    TypeDocument toTypeDocument(TypeDocumentDTO typeDocumentDTO);
}
