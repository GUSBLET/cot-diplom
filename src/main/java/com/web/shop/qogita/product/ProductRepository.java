package com.web.shop.qogita.product;

import com.web.shop.qogita.product.enums.Filtering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT id, jsonb_array_elements_text(images) AS imageUrl, BRAND, NAME, PRICES->>'SALES_PRICE' " +
            "FROM Products p " +
            "WHERE :name IS NULL OR p.name LIKE CONCAT('%', :name, '%') ", nativeQuery = true)
    Page<Object[]> findAllByNameLikeShowcaseDTO(
            @Param("name") String name,
            Pageable pageable);

    @Query(value = "SELECT p FROM Product p ORDER BY FUNCTION('RANDOM') LIMIT 3")
    List<Product> findRandomProducts();


}

