package com.web.shop.qogita.product;

import com.web.shop.qogita.order.product.OrderedProduct;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VENDOR", nullable = false)
    private String vendor;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @Column(name = "GTIN", nullable = false)
    private String gtin;

    @Column(name = "VARIANT_ID")
    private Integer variantId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition = "JSONB")
    private String description;

    @Column(name = "PRICES", nullable = false, columnDefinition = "JSONB")
    private String price;

    @Column(name = "CHARACTERISTICS", columnDefinition = "JSONB")
    private String characteristics;

    @Column(name = "PROPERTIES", columnDefinition = "JSONB")
    private String properties;

    @Column(name = "IN_STOCK", nullable = false)
    private boolean inStock;

    @Column(name = "IMAGES", nullable = false, columnDefinition = "JSONB")
    private String images;

    @Column(name = "CATEGORIES", columnDefinition = "JSONB")
    private String categories;

    @Column(name = "PRODUCT_STATE", nullable = false, columnDefinition = "JSONB")
    private String productState;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderedProduct> orderedProducts = new HashSet<>();
}