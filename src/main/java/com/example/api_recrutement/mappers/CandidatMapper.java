package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.CandidatDTO;
import com.example.api_recrutement.models.Candidat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CandidatMapper {
    CandidatMapper INSTANCE = Mappers.getMapper(CandidatMapper.class);

    CandidatDTO toCandidatDTO(Candidat candidat);

    Candidat toCandidat(CandidatDTO candidatDTO);
}
