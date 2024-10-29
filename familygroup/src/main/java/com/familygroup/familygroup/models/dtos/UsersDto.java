package com.familygroup.familygroup.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UsersDto(
    
    Long id,

    @NotBlank @NotNull @NotEmpty
    String username,

    @NotBlank @NotNull @NotEmpty
    String email,

    @NotBlank @NotNull @NotEmpty
    String password
) {
    
}
