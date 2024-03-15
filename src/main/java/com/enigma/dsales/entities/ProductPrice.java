package com.enigma.dsales.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_product_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "stock", columnDefinition = "int check(stock>0)",nullable = false)
    private Integer stock;
    @Column(name = "is_active",nullable = false)
    private boolean isActive;
    @Column(name = "price", columnDefinition = "bigint check(price>0)",nullable = false)
    private Long price;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
