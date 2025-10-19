package com.example.bankcards.service.auth;

import com.example.bankcards.dto.auth.RegisterUserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegisterUserDto dto) {
        if (this.userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NoSuchElementException("Role USER not found"));

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(this.passwordEncoder.encode(dto.password()));
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setRoles(new HashSet<>(List.of(userRole)));

        return this.userRepository.save(user);
    }
}

