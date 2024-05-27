package com.web.shop.qogita.order;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.web.shop.qogita.account.Account;
import com.web.shop.qogita.account.AccountService;
import com.web.shop.qogita.order.dto.*;
import com.web.shop.qogita.order.dto.displaying.BasketOrderDTO;
import com.web.shop.qogita.order.dto.displaying.OrderDetailsDTO;
import com.web.shop.qogita.order.dto.displaying.OrderPanelFilterDTO;
import com.web.shop.qogita.order.dto.updating.UpdatingAdminPanelDTO;
import com.web.shop.qogita.order.dto.updating.UpdatingOrderedProductDTO;
import com.web.shop.qogita.order.product.OrderedProduct;
import com.web.shop.qogita.order.product.OrderedProductRepository;
import com.web.shop.qogita.product.Product;
import com.web.shop.qogita.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final AccountService accountService;

    public void addProductToOrder(@NotNull AddingProductToOrderDTO dto) {
        Product product = productService.findProductById(dto.getId());
        if (product == null)
            return;

        Order order = findPlannedOrderIfNotExistOrCreate();
        Optional<OrderedProduct> optionalOrderedProduct = order.getOrderedProducts().stream()
                .filter(o -> o.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (optionalOrderedProduct.isEmpty())
            addNewItem(dto, product, order);
        else
            margeItem(dto, optionalOrderedProduct.get(), order);
    }

    public void processOrder(UpdatingAdminPanelDTO dto) {
        if (dto.getArrivalDate() == null){
            orderRepository.updateStatusAndFinalPriceAndArrivingTimeById(dto.getStatus(), dto.getFinalPrice(), null, dto.getId());
            return;
        }
        orderRepository.updateStatusAndFinalPriceAndArrivingTimeById(dto.getStatus(), dto.getFinalPrice(), dto.getArrivalDate().atStartOfDay(), dto.getId());
    }

    public boolean makeOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty() || order.get().getCommonPrice().floatValue() == 0)
            return false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(order.get().getAccount().getEmail(), authentication.getName()))
            return false;


        orderRepository.updateStatusById(OrderStatus.ORDERED, id);
        return true;
    }

    public void updateSelectedProductFromPlannedOrder(@NotNull UpdatingOrderedProductDTO dto) {
        Optional<OrderedProduct> orderedProduct = orderedProductRepository.findById(UUID.fromString(dto.getId()));
        if (orderedProduct.isEmpty())
            return;

        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow();
        if (dto.getAmount() == 0) {
            order.setCommonPrice(BigDecimal.valueOf(dto.getNewCommonPrice()));
            orderRepository.updateOrderCommonPrice(order.getId(), order.getCommonPrice().floatValue());
            orderedProductRepository.deleteByIdNative(orderedProduct.get().getId().toString());
            return;
        }

        orderedProduct.get().setAmount(dto.getAmount());
        orderedProductRepository.updateAmount(orderedProduct.get().getId(), orderedProduct.get().getAmount());

        order.setCommonPrice(BigDecimal.valueOf(dto.getNewCommonPrice()));
        orderRepository.updateOrderCommonPrice(order.getId(), order.getCommonPrice().floatValue());
    }

    public Page<Order> getOrdersPage(@NotNull OrderPanelFilterDTO dto) {
        return orderRepository.findByIdAndStatus(dto.getId(), dto.getStatus(), dto.getPageable());
    }

    public List<Order> getUserOrderHistoryList() {
        String email = accountService.getUserEmailFromSession();
        return orderRepository.findAllByAccountEmailAndNotStatusAndCreationTimeDESC(email, OrderStatus.PLANNED);
    }

    public OrderDetailsDTO getOrderDetails(Long id) {
        String email = accountService.getUserEmailFromSession();
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty() || !Objects.equals(order.get().getAccount().getEmail(), email))
            return null;
        OrderDetailsDTO dto = new OrderDetailsDTO();
        return dto.toDto(order.get());
    }

    public OrderDetailsDTO getOrderDetailsProcessing(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            return null;
        OrderDetailsDTO dto = new OrderDetailsDTO();
        return dto.toDto(order.get());
    }

    public BasketOrderDTO getUserBasket() {
        String email = accountService.getUserEmailFromSession();
        Optional<Order> order = orderRepository.findByAccountEmailAndStatusWithOrderedProducts(email, OrderStatus.PLANNED);
        if (order.isEmpty())
            return null;

        BasketOrderDTO dto = new BasketOrderDTO();
        return dto.toDto(order.get());
    }

    private OrderedProduct convertProductToOrderedProduct(@NotNull Product product, AddingProductToOrderDTO dto) {
        Gson gson = new Gson();
        JsonObject jsonPrices = gson.fromJson(product.getPrice(), JsonObject.class);
        return OrderedProduct.builder()
                .id(UUID.randomUUID())
                .amount(dto.getAmount())
                .price(new BigDecimal(jsonPrices.get("SALES_PRICE").getAsString())) // Parse as BigDecimal
                .product(product)
                .build();
    }

    private Order findPlannedOrderIfNotExistOrCreate() {
        String email = accountService.getUserEmailFromSession();
        Optional<Order> order = orderRepository.findByAccountEmailAndStatus(email, OrderStatus.PLANNED);
        return order.orElseGet(() -> createOrder(email));
    }

    private Order createOrder(String email) {
        Account account = accountService.findAccountByEmail(email);
        Order order = Order.builder()
                .id(orderRepository.findMaxIdOrDefault() + 1)
                .status(OrderStatus.PLANNED)
                .creationTime(LocalDateTime.now())
                .commonPrice(BigDecimal.valueOf(0F))
                .account(account)
                .orderedProducts(new HashSet<>())
                .build();
        orderRepository.saveOrderDetails(order.getId(), order.getStatus().toString(), order.getCreationTime(), order.getCommonPrice().floatValue(), account.getId());
        return order;
    }

    private BigDecimal priceCalculation(BigDecimal commonPrice, Integer newAmount, @NotNull OrderedProduct orderedProduct) {
        if (newAmount == orderedProduct.getAmount())
            return commonPrice;

        BigDecimal oldOrderedProductsPrice = orderedProduct.getPrice().multiply(BigDecimal.valueOf(orderedProduct.getAmount()));
        commonPrice = commonPrice.subtract(oldOrderedProductsPrice);
        BigDecimal newOrderedProductsPrice = orderedProduct.getPrice().multiply(BigDecimal.valueOf(newAmount));
        commonPrice = commonPrice.add(newOrderedProductsPrice);
        return commonPrice;
    }

    private void margeItem(AddingProductToOrderDTO dto, OrderedProduct orderedProduct, Order order) {
        order.setCommonPrice(priceCalculation(order.getCommonPrice(), dto.getAmount(), orderedProduct));
        orderRepository.updateOrderDetails(order.getId(), order.getStatus(), order.getArrivingTime(), order.getCommonPrice().floatValue());

        orderedProduct.setAmount(dto.getAmount());
        orderedProductRepository.updateAmount(orderedProduct.getId(), orderedProduct.getAmount());
    }

    private void addNewItem(AddingProductToOrderDTO dto, Product product, Order order) {
        OrderedProduct orderedProduct = convertProductToOrderedProduct(product, dto);
        orderedProduct.setOrder(order);
        orderedProductRepository.save(orderedProduct);

        order.setCommonPrice(priceCalculation(order.getCommonPrice(), orderedProduct.getAmount(), OrderedProduct.builder().price(orderedProduct.getPrice()).amount( 0).build()));
        orderRepository.updateOrderDetails(order.getId(), order.getStatus(), order.getArrivingTime(), order.getCommonPrice().floatValue());
    }
}
