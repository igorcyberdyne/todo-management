package com.javaguides.todomanagement.repository;

import com.javaguides.todomanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
