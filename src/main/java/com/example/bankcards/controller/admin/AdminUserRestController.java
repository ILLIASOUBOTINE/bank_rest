package com.example.bankcards.controller.admin;


import com.example.bankcards.dto.user.CreateUserDto;
import com.example.bankcards.dto.user.UpdateUserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/users")
public class AdminUserRestController {

    private final AdminUserService adminUserService ;

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(this.adminUserService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return this.adminUserService.findUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto dto) {

        User createdUser = this.adminUserService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId,
                                           @RequestBody UpdateUserDto dto) {
        this.adminUserService.updateUser(userId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        this.adminUserService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{userId}/roles")
    public ResponseEntity<Void> setUserRoles(@PathVariable Long userId,
                                             @RequestBody List<String> roleNames) {

        this.adminUserService.setRoles(userId, roleNames);
        return ResponseEntity.noContent().build();
    }
}
