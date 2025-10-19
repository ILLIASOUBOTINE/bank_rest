package com.example.bankcards.service.admin;


import com.example.bankcards.dto.user.UpdateUserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.dto.user.CreateUserDto;
import com.example.bankcards.exception.AlreadyExistsException;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return Optional.of(user);
    }

    @Override
    @Transactional
    public User createUser(CreateUserDto userDto) {

        if (this.userRepository.existsByUsername(userDto.username())) {
            throw new AlreadyExistsException("Username already exists");
        }

        List<Role> roles = this.roleRepository.findByNameIn(userDto.roleNames());

        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(this.passwordEncoder.encode(userDto.password()));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setRoles(new HashSet<>(roles));

        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UpdateUserDto userDto) {
         this.userRepository.findById(userId)
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
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        this.userRepository.delete(user);
    }

    @Override
    @Transactional
    public void setRoles(Long userId, List<String> roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<Role> roles = this.roleRepository.findByNameIn(roleName);

        user.setRoles(new HashSet<>(roles));
        this.userRepository.save(user);
    }
}
