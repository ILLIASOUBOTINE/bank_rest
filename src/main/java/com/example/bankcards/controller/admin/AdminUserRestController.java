package com.example.bankcards.controller.admin;

import com.example.bankcards.entity.User;
import com.example.bankcards.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/users")
public class AdminUserRestController {

    private final AdminUserService adminUserService ;

    @GetMapping("/list")
    public Iterable<User> findUsers() {
            return this.adminUserService.findAllUsers();
        }


}
