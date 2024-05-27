package com.web.shop.qogita.order.dto.displaying;

import com.web.shop.qogita.order.OrderStatus;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPanelFilterDTO {
    private Long id;
    private OrderStatus status;
    private Pageable pageable;
}
