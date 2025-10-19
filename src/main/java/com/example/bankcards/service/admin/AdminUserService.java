package com.example.bankcards.service.admin;


import com.example.bankcards.dto.user.CreateUserDto;
import com.example.bankcards.dto.user.UpdateUserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;

import java.util.List;
import java.util.Optional;


public interface AdminUserService {
    Iterable<User> findAllUsers();

    Optional<User> findUserById(Long userId);

    User createUser(CreateUserDto userDto);

    void updateUser(Long userId, UpdateUserDto userDto);

    void deleteUser(Long userId);

    void setRoles(Long userId, List<String> roleNames);
}
