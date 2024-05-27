package com.web.shop.qogita.order;

import com.web.shop.qogita.order.dto.AddingProductToOrderDTO;
import com.web.shop.qogita.order.dto.displaying.BasketOrderDTO;
import com.web.shop.qogita.order.dto.displaying.OrderDetailsDTO;
import com.web.shop.qogita.order.dto.displaying.OrderPanelCommonDTO;
import com.web.shop.qogita.order.dto.displaying.OrderPanelFilterDTO;
import com.web.shop.qogita.order.dto.updating.UpdatingAdminPanelDTO;
import com.web.shop.qogita.order.dto.updating.UpdatingOrderedProductDTO;
import com.web.shop.qogita.product.ProductService;
import com.web.shop.qogita.technical.message.page.MessagePageDTO;
import com.web.shop.qogita.technical.model.attribute.ModelAttributeManager;
import com.web.shop.qogita.technical.model.attribute.ModelPageAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;



    @GetMapping("/order/basket")
    public String getBasketPage(Model model) {
        BasketOrderDTO dto = orderService.getUserBasket();
        if (dto == null) {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Basket")
                    .content("basket-empty-page")
                    .entity(productService.getRandomCards())
                    .build());
        } else {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Basket")
                    .content("basket-page")
                    .entity(dto)
                    .build());
        }
        return "layout";
    }

    @GetMapping("/order/add-item")
    public String addItem(@RequestParam Long id, @RequestParam Integer amount, Model model) {
        orderService.addProductToOrder(AddingProductToOrderDTO.builder()
                .id(id)
                .amount(amount)
                .build());

        ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Success")
                .content("message-page")
                .entity(MessagePageDTO.builder()
                        .title("Success")
                        .message("Item added to your basket.")
                        .build())
                .build());
        return "layout";
    }

    @GetMapping("/order/make-order")
    public String makeOrder(@RequestParam Long id, Model model) {
        MessagePageDTO dto;
        if (orderService.makeOrder(id)) {
            dto = MessagePageDTO.builder()
                    .title("Success")
                    .message("The order has been ordered.")
                    .build();
        } else {
            dto = MessagePageDTO.builder()
                    .title("Error")
                    .message("Something was wrong.")
                    .build();
        }

        ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Info")
                .content("message-page")
                .entity(dto)
                .build());
        return "layout";
    }

    @GetMapping("/order/orders-history")
    public String getUserOrderHistory(Model model) {
        ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Info")
                .content("order-history-page")
                .entity(orderService.getUserOrderHistoryList())
                .build());

        return "layout";
    }

    @GetMapping("/order/order-details")
    public String getOrderDetails(@RequestParam Long id, Model model) {
        OrderDetailsDTO dto = orderService.getOrderDetails(id);
        if (dto == null) {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Info")
                    .content("message-page")
                    .entity(MessagePageDTO.builder()
                            .title("Error")
                            .message("You don't have access.")
                            .build())
                    .build());
        } else {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Info")
                    .content("order-details-page")
                    .entity(dto)
                    .build());
        }

        return "layout";
    }

    @GetMapping("/admin/order/orders-panel")
    public String getOrdersPanel(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "30") int size,
                                 @RequestParam(name = "id", defaultValue = "") Long id,
                                 @RequestParam(name = "status", defaultValue = "") OrderStatus status,
                                 Model model) {
        OrderPanelFilterDTO filter = OrderPanelFilterDTO.builder()
                .pageable(PageRequest.of(page, size))
                .status(status)
                .id(id)
                .build();

        ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Orders panel")
                .content("orders-panel-page")
                .entity(OrderPanelCommonDTO.builder()
                        .filter(filter)
                        .orders(orderService.getOrdersPage(filter))
                        .build())
                .build());
        orderService.getOrdersPage(filter);

        return "layout";
    }

    @PostMapping("/admin/order/process-order")
    public RedirectView processOrder( @ModelAttribute UpdatingAdminPanelDTO dto){
        orderService.processOrder(dto);
        return new RedirectView("/admin/order/order-processing?id=" + dto.getId());
    }

    @GetMapping("/admin/order/order-processing")
    public String getProcessingOrder(@RequestParam Long id, Model model) {
        OrderDetailsDTO dto = orderService.getOrderDetailsProcessing(id);
        if (dto == null) {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Info")
                    .content("message-page")
                    .entity(MessagePageDTO.builder()
                            .title("Error")
                            .message("Something was wrong.")
                            .build())
                    .build());
        } else {
            UpdatingAdminPanelDTO adminPanelDTO = new UpdatingAdminPanelDTO();
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Info")
                    .content("order-processing-page")
                    .entity(dto)
                    .build());
            model.addAttribute("update", adminPanelDTO.toDto(dto));
        }

        return "layout";
    }

    @PostMapping("/order/update-item")
    public ResponseEntity<Void> updateItem(@RequestBody UpdatingOrderedProductDTO dto) {
        orderService.updateSelectedProductFromPlannedOrder(dto);
        return ResponseEntity.ok().build();
    }
}
