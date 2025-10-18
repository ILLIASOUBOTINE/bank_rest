package com.example.bankcards.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateUserDto(
        @NotBlank(message = "Имя пользователя не может быть пустым")
        @Size(min = 1, max = 50, message = "Имя пользователя должно быть от 1 до 50 символов")
        String username,

        @NotBlank(message = "Пароль обязателен")
        @Size(max = 100, message = "Пароль должен быть не более 100 символов")
        String password,

        @Size(max = 50, message = "First Name не более 50 символов")
        String firstName,
        @Size(max = 50, message = "Last Name не более 50 символов")
        String lastName
) {

}
