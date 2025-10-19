package com.example.bankcards.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 100, message = "Fist name must be between 2 and 100 characters")
        String firstName,

        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        @NotBlank(message = "Last name is required")
        String lastName
) {
}
