package com.web.shop.qogita.order.dto.displaying;

import com.web.shop.qogita.order.Order;
import com.web.shop.qogita.order.OrderStatus;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsDTO implements Mapper<OrderDetailsDTO, Order> {
    private Set<OrderedProductDetailsDTO> products;
    private LocalDateTime arrivingTime;
    private LocalDateTime creationTime;
    private Float commonPrice;
    private Float finalPrice;
    private OrderStatus status;
    private String email;
    private Long id;
    @Override
    public OrderDetailsDTO toDto(Order entity) {
        OrderedProductDetailsDTO dto = new OrderedProductDetailsDTO();
        BigDecimal s = entity.getFinalPrice();
        BigDecimal qw = entity.getCommonPrice();

        float s_res;
        float qw_res;
        if (s == null)
            s_res = 0;
        else
            s_res = s.floatValue();

        if (qw == null)
            qw_res = 0;
        else
            qw_res = qw.floatValue();

        return OrderDetailsDTO.builder()
                .id(entity.getId())
                .arrivingTime(entity.getArrivingTime())
                .commonPrice(qw_res)
                .finalPrice(s_res)
                .products(dto.toDtoSet(entity.getOrderedProducts()))
                .status(entity.getStatus())
                .creationTime(entity.getCreationTime())
                .email(entity.getAccount().getEmail())
                .build();
    }

    @Override
    public Order toEntity(OrderDetailsDTO dto) {
        return null;
    }
}
