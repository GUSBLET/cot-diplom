package com.web.shop.qogita.product.dto;

import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowcaseDTO implements Mapper<ShowcaseDTO, Object[]> {
    private Long id;
    private String imageUrl;
    private String brandName;
    private String name;
    private String price;

    @Override
    public ShowcaseDTO toDto(Object[] entity) {
        return ShowcaseDTO.builder()
                .id((Long) entity[0])
                .imageUrl((String) entity[1])
                .brandName((String) entity[2])
                .name((String) entity[3])
                .price((String) entity[4])
                .build();
    }

    @Override
    public Object[] toEntity(ShowcaseDTO dto) {
        return new Object[0];
    }
}
