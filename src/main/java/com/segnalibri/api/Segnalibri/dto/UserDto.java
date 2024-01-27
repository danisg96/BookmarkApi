package com.segnalibri.api.Segnalibri.dto;

import com.segnalibri.api.Segnalibri.model.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto (String email, String password, LocalDateTime createdAt){
}
