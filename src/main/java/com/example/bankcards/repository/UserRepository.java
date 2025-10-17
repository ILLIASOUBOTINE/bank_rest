package com.example.bankcards.repository;

import com.example.bankcards.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
