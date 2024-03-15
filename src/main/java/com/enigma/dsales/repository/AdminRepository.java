package com.enigma.dsales.repository;

import com.enigma.dsales.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
    @Modifying
    @Query(value = "INSERT INTO m_admin (id, name, email, phone_number, created_at, user_credential_id) " +
            "VALUES (gen_random_uuid(), :name, :email, :phoneNumber, :createdAt, :userCredentialId)", nativeQuery = true)
    void insertAdminNative(@Param("name") String name,
                            @Param("email") String email,
                            @Param("phoneNumber") String phoneNumber,
                            @Param("createdAt") LocalDateTime createdAt,
                           @Param("userCredentialId") String userCredentialId);
    @Query(value = "SELECT * FROM m_admin WHERE id = :id", nativeQuery = true)
    Admin findByIdNative(@Param("id") String id);
    @Query(value = "SELECT * FROM m_admin WHERE email = :email", nativeQuery = true)
    Admin findByEmail(@Param("email") String email);
    @Query(value = "SELECT * FROM m_admin WHERE username = :username", nativeQuery = true)
    Admin findByUsername(@Param("username")String username);
    @Query(value = "SELECT id FROM m_admin ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    String findLastInsertedId();

}
