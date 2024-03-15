package com.enigma.dsales.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "is_active",nullable = false)
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "product_detail_id",unique = true)
    @JsonBackReference
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "product_price_id",unique = true)
    @JsonBackReference
    private ProductPrice productPrice;
}
