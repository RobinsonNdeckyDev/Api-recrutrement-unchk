package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.AdminDTO;
import com.example.api_recrutement.models.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminDTO toAdminDTO(Admin admin);

    Admin toAdmin(AdminDTO adminDTO);
}
