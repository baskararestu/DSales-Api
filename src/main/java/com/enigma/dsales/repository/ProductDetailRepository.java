package com.enigma.dsales.repository;

import com.enigma.dsales.entities.ProductDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_product_details (id, name, description, is_active, created_at, category_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)", nativeQuery = true)
    void insertProductDetailNative
            (@Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("is_active") Boolean isActive,
            @Param("created_at") LocalDateTime createdAt,
            @Param("category_id") String categoryId);
    @Query(value = "SELECT * FROM m_product_details WHERE id = :id", nativeQuery = true)
    Optional<ProductDetail> findProductDetailByIdNative(@Param("id") String id);

    @Modifying
    @Query(value = "UPDATE m_product_details SET is_active = false, category_id = null WHERE id = :productDetailId", nativeQuery = true)
    void softDeleteProduct(@Param("productDetailId") String productDetailId);

    ProductDetail findProductDetailByName(String productName);
}
