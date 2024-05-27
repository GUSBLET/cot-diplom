package com.web.shop.qogita.order.dto.updating;

import com.web.shop.qogita.order.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatingOrderStatusDTO {
    private Long orderId;
    private OrderStatus status;
}
