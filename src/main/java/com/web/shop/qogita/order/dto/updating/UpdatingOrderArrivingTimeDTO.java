package com.web.shop.qogita.order.dto.updating;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatingOrderArrivingTimeDTO {
    private Long orderId;
    private LocalDateTime arrivingTime;
}
