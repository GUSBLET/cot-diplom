package com.web.shop.qogita.order.dto.displaying;

import com.web.shop.qogita.order.Order;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPanelCommonDTO{
    private OrderPanelFilterDTO filter;
    private Page<Order> orders;
}
