package com.web.shop.qogita.order.dto;

import com.web.shop.qogita.order.product.OrderedProduct;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddingProductToOrderDTO implements Mapper<AddingProductToOrderDTO, OrderedProduct> {
    private Long id;
    private Integer amount;

    @Override
    public AddingProductToOrderDTO toDto(OrderedProduct entity) {
        return null;
    }

    @Override
    public OrderedProduct toEntity(AddingProductToOrderDTO dto) {
        return null;
    }
}
