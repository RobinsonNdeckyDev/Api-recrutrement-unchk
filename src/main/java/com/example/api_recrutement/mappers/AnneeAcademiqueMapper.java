package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.AnneeAcademiqueDTO;
import com.example.api_recrutement.models.AnneeAcademique;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Year;

@Mapper
public interface AnneeAcademiqueMapper {
    AnneeAcademiqueMapper INSTANCE = Mappers.getMapper(AnneeAcademiqueMapper.class);

    AnneeAcademiqueDTO anneeAcademiqueToAnneeAcademiqueDTO(AnneeAcademiqueDTO anneeAcademique);

    AnneeAcademique anneeAcademiqueDTOToAnneeAcademique(AnneeAcademiqueDTO anneeAcademiqueDTO);

    default Year map(String value) {
        return Year.parse(value);
    }

    default String map(Year value) {
        return value.toString();
    }
}
