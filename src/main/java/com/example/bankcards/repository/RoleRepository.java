package com.example.bankcards.repository;

import com.example.bankcards.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Integer> {
    List<Role> findByNameIn(List<String> names);
    Optional<Role> findByName(String name);
}
