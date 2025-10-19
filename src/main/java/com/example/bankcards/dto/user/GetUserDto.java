package com.example.bankcards.dto.user;


import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;


import java.util.Set;

public record GetUserDto(
        Long userId,
        String username,
        String firstName,
        String lastName,
        Set<Role> roles
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
