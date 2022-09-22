package com.security.springsecuritydemo.repository;

import com.security.springsecuritydemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
