package com.web.shop.qogita.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PLANNED("Planned"),
    ORDERED("Ordered"),
    UPDATED("Updated"),
    CANCELED("Canceled"),
    SHIPPED("Shipped"),
    PROCESSED("Processed"),
    ARRIVED("Arrived"),
    OUT_FOR_DELIVERY("Out for delivery");

    private final String displayName;
}
