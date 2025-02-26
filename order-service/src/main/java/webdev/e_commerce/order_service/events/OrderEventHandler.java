package webdev.e_commerce.order_service.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import webdev.e_commerce.common.Status;
import webdev.e_commerce.common.events.order.OrderCancelledEvent;
import webdev.e_commerce.common.events.order.OrderCompletedEvent;
import webdev.e_commerce.order_service.service.OrderService;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class OrderEventHandler {
    private final OrderService orderService;

    @EventHandler
    void on(OrderCompletedEvent event) {
        orderService.changeStatus(UUID.fromString(event.getId()), Status.COMPLETED);
    }

    @EventHandler
    void on(OrderCancelledEvent event) {
        orderService.changeStatus(UUID.fromString(event.getId()), Status.CANCELLED);
    }
}
