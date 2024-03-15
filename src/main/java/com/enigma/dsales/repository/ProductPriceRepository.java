package com.enigma.dsales.repository;

import com.enigma.dsales.entities.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, String> {
    @Modifying
    @Query(value = "INSERT INTO m_product_price (id, is_active, price, stock, created_at) " +
            "VALUES (?, ?, ?, ?, ?)", nativeQuery = true)
    void insertProductPriceNative(
            @Param("id") String id,
            @Param("is_active") Boolean isActive,
            @Param("price") Long price,
            @Param("stock") Integer stock,
            @Param("created_at") LocalDateTime createdAt
    );
    @Query(value = "SELECT * FROM m_product_price WHERE id = :id", nativeQuery = true)
    Optional<ProductPrice> findProductPricesByIdNative(@Param("id") String id);
    @Modifying
    @Query(value = "UPDATE m_product_price SET is_active = false WHERE id = :productPriceId", nativeQuery = true)
    void softDeleteProduct(@Param("productPriceId") String productPriceId);
}

