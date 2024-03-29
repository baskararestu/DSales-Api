package com.enigma.dsales.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_product_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active",nullable = false)
    private Boolean isActive;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

}
