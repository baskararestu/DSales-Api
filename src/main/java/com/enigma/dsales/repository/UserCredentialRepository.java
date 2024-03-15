package com.enigma.dsales.repository;

import com.enigma.dsales.entities.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential,String> {
    Optional<UserCredential>findByUsername(String username);
}
