package com.example.bankcards.dto.user;


import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;

import java.util.List;

public record GetUserDto(
        Long userId,
        String username,
        String firstName,
        String lastName,
        List<Role> roles
    )
{
    public static GetUserDto fromEntity(User user) {
        return new GetUserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles()
        );
    }
}
