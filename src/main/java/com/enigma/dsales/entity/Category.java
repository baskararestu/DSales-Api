package com.enigma.dsales.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_category")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
