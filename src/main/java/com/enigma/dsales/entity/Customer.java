package com.enigma.dsales.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    private String address;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,length = 30)
    private String phoneNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_credential_id",nullable = false)
    private UserCredential userCredential;

}
