package com.segnalibri.api.Segnalibri.mapper;

import com.segnalibri.api.Segnalibri.dto.UserDto;
import com.segnalibri.api.Segnalibri.model.Role;
import com.segnalibri.api.Segnalibri.model.User;
import org.springframework.stereotype.Component;

import static com.segnalibri.api.Segnalibri.model.Role.ADMIN;

@Component
public class UserMapper {
    public User DtoToEntity(UserDto userDto){
        return User
                .builder()
                .email(userDto.email())
                .password(userDto.password())//Aggiungere eventuali encrypting
                .createDate(userDto.createdAt())
                .role(ADMIN)
                .build();
    }

    public UserDto EntityToDto(User user){
        return UserDto
                .builder()
                .email(user.getEmail())
                .password("***")
                .createdAt(user.getCreateDate())
                .build();
    }
}
