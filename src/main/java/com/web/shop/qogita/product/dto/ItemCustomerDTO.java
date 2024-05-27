package com.web.shop.qogita.product.dto;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.web.shop.qogita.product.Product;
import com.web.shop.qogita.product.enums.PriceType;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCustomerDTO implements Mapper<ItemCustomerDTO, Product> {
    private Long id;
    private String name;
    private String brand;
    private String image;
    private String gtin;
    private List<Float> prices;
    private Boolean inStock;
    private Map<String, String> characteristics;


    @Override
    public ItemCustomerDTO toDto(Product entity) {
        return ItemCustomerDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .image(getImage(entity.getImages()))
                .gtin(entity.getGtin())
                .prices(getPricesToArray(entity.getPrice()))
                .inStock(entity.isInStock())
                .characteristics(mappingCharacteristics(entity.getCharacteristics()))
                .build();
    }

    @Override
    public Product toEntity(ItemCustomerDTO dto) {
        return null;
    }

    private String getImage(String images){
        Gson gson = new Gson();
        String[] jsonArray = gson.fromJson(images, String[].class);

        if(jsonArray.length > 0)
            return jsonArray[0];
        return null;
    }

    private List<Float> getPricesToArray(String prices){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(prices, JsonObject.class);
        if (jsonObject == null || !jsonObject.isJsonObject())
            return null;

        List<Float> arrayPrices = new ArrayList<>();

        arrayPrices.add(jsonObject.get(PriceType.PURCHASE_PRICE.name()).getAsFloat());
        arrayPrices.add(jsonObject.get(PriceType.RETAIL_PRICE.name()).getAsFloat());
        arrayPrices.add(jsonObject.get(PriceType.SALES_PRICE.name()).getAsFloat());

        return arrayPrices;
    }

    private Map<String, String> mappingCharacteristics(String properties) {
        Map<String, String> map = new HashMap<>();
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(properties, JsonElement.class);
        if (jsonElement == null || !jsonElement.isJsonObject())
            return null;

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey().replaceAll("_", " ").toLowerCase();
            String value = entry.getValue().toString().replaceAll("\"", "");
            map.put(key, value);
        }

        return map;
    }
}
