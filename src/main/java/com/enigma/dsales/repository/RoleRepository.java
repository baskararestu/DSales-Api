package com.enigma.dsales.repository;

import com.enigma.dsales.constant.ERole;
import com.enigma.dsales.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Optional<Role> findByRoleName(ERole roleName);
}
