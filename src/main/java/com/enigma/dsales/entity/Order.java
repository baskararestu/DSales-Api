package com.enigma.dsales.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long totalAmount;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    @JsonBackReference
    private Cart cart;
}
