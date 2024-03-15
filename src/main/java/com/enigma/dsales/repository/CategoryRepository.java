package com.enigma.dsales.repository;

import com.enigma.dsales.dto.response.CategoryResponse;
import com.enigma.dsales.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
   @Modifying
   @Transactional
    @Query(value = "INSERT INTO m_category (id, name, created_at) " +
            "VALUES (:id, :name, :createdAt)", nativeQuery = true)
    void insertCategoryNative(@Param("id") String id,
                              @Param("name") String name,
                              @Param("createdAt") LocalDateTime createdAt);

    @Query(value = "SELECT * FROM m_category WHERE id = :id", nativeQuery = true)
    Category findByIdNative(@Param("id") String id);
    @Query(value = "SELECT * FROM m_category WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    Page<Category> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE m_category SET name = :name WHERE id = :id", nativeQuery = true)
    void updateCategoryNameNative(@Param("id") String id, @Param("name") String name);

    @Modifying
    @Query(value = "DELETE FROM m_category WHERE id = :id", nativeQuery = true)
    void deleteByIdNative(@Param("id") String id);

    @Query(value = "SELECT * FROM m_category WHERE name = :name", nativeQuery = true)
    CategoryResponse findCategoryByNameNative(@Param("name") String name);
}
