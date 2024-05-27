package com.web.shop.qogita.order.product;

import com.web.shop.qogita.order.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, UUID> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE ORDERED_PRODUCTS SET AMOUNT = :newAmount WHERE ID = :orderedProductId", nativeQuery = true)
    void updateAmount(@Param("orderedProductId") UUID orderedProductId, @Param("newAmount") Integer newAmount);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ORDERED_PRODUCTS WHERE ID = :id", nativeQuery = true)
    void deleteByIdNative(@Param("id") String id);
}
