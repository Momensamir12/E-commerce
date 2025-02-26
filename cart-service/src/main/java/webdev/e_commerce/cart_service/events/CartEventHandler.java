package webdev.e_commerce.cart_service.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import webdev.e_commerce.cart_service.service.CartService;
import webdev.e_commerce.common.events.order.OrderCreatedEvent;
import webdev.e_commerce.common.events.product.ProductOutOfStockEvent;
import webdev.e_commerce.common.events.product.ProductPriceChangedEvent;

@Component
@AllArgsConstructor
@Slf4j
public class CartEventHandler {
    private final CartService cartService;

    @EventHandler
    void on(OrderCreatedEvent orderCreatedEvent) {
        String id = orderCreatedEvent.getBuyerId();
        cartService.deleteCart(id);
        log.info("Cart Cleared for User" + id);
    }

    @EventHandler
    void on(ProductOutOfStockEvent productOutOfStockEvent) {
        cartService.deleteItemFromCarts(productOutOfStockEvent.getProductId());
    }

    @EventHandler
    void on(ProductPriceChangedEvent event) {
        cartService.updateItemPrice(event.getProductId(), event.getPrice());
    }
}
