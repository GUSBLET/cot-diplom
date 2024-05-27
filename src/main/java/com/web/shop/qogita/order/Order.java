package com.web.shop.qogita.order;

import com.web.shop.qogita.account.Account;
import com.web.shop.qogita.order.product.OrderedProduct;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 60, nullable = false)
    private OrderStatus status;

    @Column(name = "CREATION_TIME", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationTime;

    @Column(name = "ARRIVING_TIME")
    private LocalDateTime arrivingTime;

    @Column(name = "COMMON_PRICE", precision = 10, scale = 2)
    private BigDecimal commonPrice;

    @Column(name = "FINAL_PRICE", precision = 10, scale = 2)
    private BigDecimal finalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderedProduct> orderedProducts = new HashSet<>();
}