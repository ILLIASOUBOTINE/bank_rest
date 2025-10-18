package com.example.bankcards.dto.user;


import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @Size(max = 50, message = "First Name не более 50 символов")
        String firstName,
        @Size(max = 50, message = "Last Name не более 50 символов")
        String lastName
)
{ }
