package com.web.shop.qogita.product.dto;

import com.web.shop.qogita.product.enums.Filtering;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDTO {
    private String searchLine;
    private String brand;
    private Float minPrice;
    private Float maxPrice;
    private Filtering priceFiltering;
    private Pageable pageable;
}

