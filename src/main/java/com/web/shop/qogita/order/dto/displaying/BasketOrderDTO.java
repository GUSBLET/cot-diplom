package com.web.shop.qogita.order.dto.displaying;

import com.web.shop.qogita.order.Order;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketOrderDTO implements Mapper<BasketOrderDTO, Order> {
    private Long id;
    private float commonPrice;
    private Set<BasketOrderedProductDTO> basketOrderedProductDTO = new HashSet<>();
    @Override
    public BasketOrderDTO toDto(Order entity) {
        BasketOrderedProductDTO dto = new BasketOrderedProductDTO();
        return BasketOrderDTO.builder()
                .id(entity.getId())
                .commonPrice(entity.getCommonPrice().floatValue())
                .basketOrderedProductDTO(dto.toDtoSet(entity.getOrderedProducts()))
                .build();
    }

    @Override
    public Order toEntity(BasketOrderDTO dto) {
        return null;
    }
}
