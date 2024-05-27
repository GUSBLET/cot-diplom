package com.web.shop.qogita.product.dto;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.web.shop.qogita.product.Product;
import com.web.shop.qogita.product.enums.PriceType;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RandomCartDTO implements Mapper<RandomCartDTO, Product> {
    private Long id;
    private String name;
    private String brand;
    private String salePrice;
    private String image;

    @Override
    public RandomCartDTO toDto(Product entity) {
        return RandomCartDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .salePrice(getSalePrice(entity.getPrice()))
                .image(getImage(entity.getImages()))
                .build();
    }

    @Override
    public Product toEntity(RandomCartDTO dto) {
        return null;
    }

    private String getImage(String images){
        Gson gson = new Gson();
        String[] jsonArray = gson.fromJson(images, String[].class);

        if(jsonArray.length > 0)
            return jsonArray[0];
        return null;
    }

    private String getSalePrice(String prices){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(prices, JsonElement.class);
        if (jsonElement == null || !jsonElement.isJsonObject())
            return null;

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.get(PriceType.SALES_PRICE.toString()).getAsString();
    }
}
