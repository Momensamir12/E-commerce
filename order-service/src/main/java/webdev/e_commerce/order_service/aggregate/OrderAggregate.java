package webdev.e_commerce.order_service.aggregate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import webdev.e_commerce.common.Status;
import webdev.e_commerce.common.commands.CompleteOrderCommand;
import webdev.e_commerce.common.dto.OrderItemDTO;
import webdev.e_commerce.common.events.order.OrderCancelledEvent;
import webdev.e_commerce.common.events.order.OrderCompletedEvent;
import webdev.e_commerce.common.events.order.OrderCreatedEvent;
import webdev.e_commerce.order_service.commands.CancelOrderCommand;
import webdev.e_commerce.order_service.commands.CreateOrderCommand;
import webdev.e_commerce.order_service.dto.request.OrderItemRequestDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OrderAggregate {
    @AggregateIdentifier
    private String id;
    private String buyerId;
    private List<OrderItemDTO> orderedItems;
    private Status status;
    private String address;
    private BigDecimal totalAmount;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent
                .builder()
                .id(command.getId())
                .address(command.getAddress()).status(command.getStatus()).buyerId(command.getBuyerId()).
                totalAmount(command.getTotalAmount())
                .build();
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (OrderItemRequestDTO items : command.getOrderedItems()) {
            orderItems.add(new OrderItemDTO(items.productId(), items.price(), items.quantity()));
        }
        orderCreatedEvent.setOrderedItems(orderItems);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.id = event.getId();
        this.address = event.getAddress();
        this.buyerId = event.getBuyerId();
        this.orderedItems = event.getOrderedItems();
        this.status = Status.APPROVED;
        this.totalAmount = event.getTotalAmount();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand cmd) {
        log.info("Order completed with id : {} ", cmd.getId());
        OrderCompletedEvent event = OrderCompletedEvent.builder().id(cmd.getId())
                .status(cmd.getStatus()).build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(CancelOrderCommand cmd) {
        OrderCancelledEvent event = new OrderCancelledEvent(cmd.getId(), cmd.getBuyerId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.status = event.getStatus();
    }
}
