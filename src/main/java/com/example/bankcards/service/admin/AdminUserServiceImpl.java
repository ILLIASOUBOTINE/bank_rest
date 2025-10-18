package com.example.bankcards.service.admin;


import com.example.bankcards.dto.user.UpdateUserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.dto.user.CreateUserDto;
import com.example.bankcards.exception.AlreadyExistsException;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return Optional.of(user);
    }

    @Override
    @Transactional
    public User createUser(CreateUserDto userDto, List<Role> roles) {

        if (this.userRepository.existsByUsername(userDto.username())) {
            throw new AlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setRoles(roles);

        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UpdateUserDto userDto) {
         this.findUserById(userId)
                 .ifPresentOrElse(user -> {
                     if (userDto.firstName() != null) user.setFirstName(userDto.firstName());
                     if (userDto.lastName() != null) user.setLastName(userDto.lastName());
                     }, () -> {
                     throw new NoSuchElementException("User not found");
                 });
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        userRepository.delete(user);
    }
}
