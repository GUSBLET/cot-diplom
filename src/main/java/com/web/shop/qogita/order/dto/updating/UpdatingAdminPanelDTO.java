package com.web.shop.qogita.order.dto.updating;

import com.web.shop.qogita.order.OrderStatus;
import com.web.shop.qogita.order.dto.displaying.OrderDetailsDTO;
import com.web.shop.qogita.technical.mapper.Mapper;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatingAdminPanelDTO implements Mapper<UpdatingAdminPanelDTO, OrderDetailsDTO> {
    private LocalDate arrivalDate;
    private Float commonPrice;
    private Float finalPrice;
    private OrderStatus status;
    private Long id;

    @Override
    public UpdatingAdminPanelDTO toDto(OrderDetailsDTO entity) {
        UpdatingAdminPanelDTOBuilder builder = UpdatingAdminPanelDTO.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .finalPrice(entity.getFinalPrice())
                .commonPrice(entity.getCommonPrice());

        if (entity.getArrivingTime() != null) {
            builder.arrivalDate(entity.getArrivingTime().toLocalDate());
        }

        return builder.build();
    }

    @Override
    public OrderDetailsDTO toEntity(UpdatingAdminPanelDTO dto) {
        return null;
    }

    @Override
    public String toString() {
        return "status=" + status +
                ", arrivalDate=" + arrivalDate +
                ", commonPrice=" + commonPrice;
    }
}
