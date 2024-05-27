package com.web.shop.qogita.order.dto;

import com.web.shop.qogita.order.Order;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakeOrderDTO implements Mapper<MakeOrderDTO, Order> {
    private Long id;
    @Override
    public MakeOrderDTO toDto(Order entity) {
        return null;
    }

    @Override
    public Order toEntity(MakeOrderDTO dto) {
        return null;
    }
}
