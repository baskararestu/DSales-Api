package com.enigma.dsales.repository;

import com.enigma.dsales.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    @Modifying
    @Query(value = "INSERT INTO t_cart_item (id, quantity, customer_id, product_id) " +
            "VALUES (gen_random_uuid(), :quantity, :customerId, :productId)", nativeQuery = true)
    void addItem(@Param("quantity") int quantity,
                 @Param("customerId") String customerId,
                 @Param("productId") String productId);
}
