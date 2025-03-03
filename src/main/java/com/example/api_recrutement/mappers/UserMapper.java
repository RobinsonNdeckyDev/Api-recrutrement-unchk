package com.example.api_recrutement.mappers;

import com.example.api_recrutement.dtos.UserDTO;
import com.example.api_recrutement.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}
