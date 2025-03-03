package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.AnnonceDTO;
import com.example.api_recrutement.models.Annonce;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Year;

@Mapper
public interface AnnonceMapper {
    AnnonceMapper INSTANCE = Mappers.getMapper(AnnonceMapper.class);

    AnnonceDTO toAnnonceDTO(Annonce annonce);

    @Mapping(source = "anneeAcademiqueId", target = "anneeAcademique.id")
    Annonce toAnnonce(AnnonceDTO annonceDTO);

    default Year map(String value) {
        return Year.parse(value);
    }

    default String map(Year value) {
        return value.toString();
    }
}
