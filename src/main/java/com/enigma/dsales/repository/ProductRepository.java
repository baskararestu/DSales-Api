package com.enigma.dsales.repository;


import com.enigma.dsales.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    @Modifying
    @Query(value = "INSERT INTO m_products (id, is_active, product_detail_id, product_price_id) " +
            "VALUES (:productId, :isActive, :productDetailId, :productPriceId)", nativeQuery = true)
    void insertProductNative(
            @Param("productId") String productId,
            @Param("isActive") Boolean isActive,
            @Param("productDetailId") String productDetailId,
            @Param("productPriceId") String productPriceId
    );

    @Query(value = "SELECT mpd.name as product_name, mc.name as category_name FROM Product mp " +
            "JOIN ProductDetail mpd ON mpd.id = mp.productDetail.id " +
            "JOIN Category mc ON mc.id = mpd.category.id " +
            "WHERE mpd.name = :productName AND mc.name = :categoryName")
    Optional<Product> findByNameAndCategory(
            @Param("productName") String productName,
            @Param("categoryName") String categoryName
    );

    @Modifying
    @Query(value = "UPDATE m_products SET is_active = false WHERE id = :productId", nativeQuery = true)
    void softDeleteProduct(@Param("productId") String productId);
    Optional<Product>findProductByProductDetailNameAndProductDetailCategoryName(String productName,String categoryName);

    Optional<Product>findProductByProductDetailNameAndIsActive(String productName, Boolean isActive);
}
