package com.web.shop.qogita.order.dto.displaying;

import com.google.gson.Gson;
import com.web.shop.qogita.order.product.OrderedProduct;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketOrderedProductDTO implements Mapper<BasketOrderedProductDTO, OrderedProduct> {

    private String id;
    private String name;
    private String image;
    private float price;
    private int amount;
    private long productId;

    @Override
    public BasketOrderedProductDTO toDto(OrderedProduct entity) {
        return BasketOrderedProductDTO.builder()
                .id(entity.getId().toString())
                .name(entity.getProduct().getName())
                .price(entity.getPrice().floatValue())
                .amount(entity.getAmount())
                .productId(entity.getProduct().getId())
                .image(getImage(entity.getProduct().getImages()))
                .build();
    }

    @Override
    public OrderedProduct toEntity(BasketOrderedProductDTO dto) {
        return null;
    }

    private String getImage(String images){
        Gson gson = new Gson();
        String[] jsonArray = gson.fromJson(images, String[].class);

        if(jsonArray.length > 0)
            return jsonArray[0];
        return null;
    }
}
