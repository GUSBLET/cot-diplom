package com.web.shop.qogita.order.dto.updating;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatingOrderedProductDTO {
    private Long orderId;
    private String id;
    private Integer amount;
    private Float newCommonPrice;
}
