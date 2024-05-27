package com.web.shop.qogita.order;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO ORDERS (id, status, creation_Time, common_Price, account_id) VALUES (:id, :status, :creationTime, :commonPrice, :accountId)", nativeQuery = true)
    void saveOrderDetails(Long id, String status, LocalDateTime creationTime, float commonPrice, UUID accountId);

    @Query("SELECT o FROM Order o WHERE o.account.email = :email")
    List<Order> findAllByAccountEmail(String email);

    @Query("SELECT COALESCE(MAX(id), 0) FROM Order")
    Long findMaxIdOrDefault();

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderedProducts op WHERE o.id = :orderId")
    Optional<Order> findByIdWithOrderedProducts(Long orderId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderedProducts op WHERE o.account.email = :email AND o.status = :status")
    Optional<Order> findByAccountEmailAndStatusWithOrderedProducts(String email, OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.account.email = :email AND o.status = :status")
    Optional<Order> findByAccountEmailAndStatus(String email, OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.account.email = :email and o.status != :status ORDER BY o.creationTime DESC")
    List<Order> findAllByAccountEmailAndNotStatusAndCreationTimeDESC(String email, OrderStatus status);

    @Query(value = "SELECT o FROM Order o WHERE (:id is null OR o.id = :id) AND (:status is null OR o.status = :status)")
    Page<Order> findByIdAndStatus(@Param("id") Long id, @Param("status") OrderStatus status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :status, o.finalPrice = :finalPrice, o.arrivingTime = :arrivingTime WHERE o.id = :orderId")
    void updateStatusAndFinalPriceAndArrivingTimeById(@Param("status") OrderStatus status, @Param("finalPrice") Float finalPrice, @Param("arrivingTime") LocalDateTime dateTime, @Param("orderId") Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateStatusById(@Param("status") OrderStatus status, @Param("orderId") Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :status, o.arrivingTime = :arrivingTime, o.commonPrice = :commonPrice WHERE o.id = :orderId")
    void updateOrderDetails(Long orderId, OrderStatus status, LocalDateTime arrivingTime, float commonPrice);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.commonPrice = :commonPrice WHERE o.id = :orderId")
    void updateOrderCommonPrice(Long orderId, float commonPrice);
}
