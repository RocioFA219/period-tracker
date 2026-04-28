package com.example.period.tracker.mapper;

import com.example.period.tracker.domain.dto.UserRequestDTO;
import com.example.period.tracker.domain.dto.UserResponseDTO;
import com.example.period.tracker.domain.model.User;

public class UserMapper {
    //De DTO a entidad(para guardar en BD)
    public static User toEntity(UserRequestDTO dto){
        return User.builder()
                .username(dto.username())
                .password(dto.password())
                .email(dto.email())
                .averageCycleLength(dto.averageCycleLength())
                .build();
    }

    //De entidad a DTO(para responder al usuario)
    public static UserResponseDTO toDto(User entity){
        return new UserResponseDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getAverageCycleLength()
        );
    }
}
