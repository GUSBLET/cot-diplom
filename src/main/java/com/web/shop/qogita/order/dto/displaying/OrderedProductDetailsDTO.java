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
public class OrderedProductDetailsDTO implements Mapper<OrderedProductDetailsDTO, OrderedProduct> {
    private Long productId;
    private Integer amount;
    private String name;
    private String image;
    private Float price;
    private String gtin;
    private String vendor;
    @Override
    public OrderedProductDetailsDTO toDto(OrderedProduct entity) {
        return OrderedProductDetailsDTO.builder()
                .name(entity.getProduct().getName())
                .productId(entity.getProduct().getId())
                .amount(entity.getAmount())
                .image(getImage(entity.getProduct().getImages()))
                .price(entity.getPrice().floatValue())
                .gtin(entity.getProduct().getGtin())
                .vendor(entity.getProduct().getVendor())
                .build();
    }

    @Override
    public OrderedProduct toEntity(OrderedProductDetailsDTO dto) {
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
