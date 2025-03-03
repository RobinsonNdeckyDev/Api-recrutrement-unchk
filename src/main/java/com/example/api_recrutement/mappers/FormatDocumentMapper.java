package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.FormatDocumentDTO;
import com.example.api_recrutement.models.FormatDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FormatDocumentMapper {
    FormatDocumentMapper INSTANCE = Mappers.getMapper(FormatDocumentMapper.class);

    FormatDocumentDTO toFormatDocumentDTO(FormatDocument formatDocument);

    FormatDocument toFormatDocument(FormatDocumentDTO formatDocumentDTO);
}
