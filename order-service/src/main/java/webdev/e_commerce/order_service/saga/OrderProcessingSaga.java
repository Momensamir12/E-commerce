package webdev.e_commerce.order_service.saga;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import webdev.e_commerce.common.Status;
import webdev.e_commerce.common.commands.CompleteOrderCommand;
import webdev.e_commerce.common.commands.CreatePaymentCommand;
import webdev.e_commerce.common.events.order.OrderCancelledEvent;
import webdev.e_commerce.common.events.order.OrderCompletedEvent;
import webdev.e_commerce.common.events.order.OrderCreatedEvent;
import webdev.e_commerce.common.events.payment.PaymentCompletedEvent;
import webdev.e_commerce.common.events.payment.PaymentFailedEvent;
import webdev.e_commerce.order_service.commands.CancelOrderCommand;
import webdev.e_commerce.order_service.commands.OrderCommandHandler;

import java.util.UUID;

@Saga
@Slf4j
@NoArgsConstructor
public class OrderProcessingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private OrderCommandHandler orderCommandHandler;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    void handle(OrderCreatedEvent event) {
        String paymentId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("paymentId", paymentId);
        commandGateway.send(new CreatePaymentCommand(paymentId, event.getId(), event.getBuyerId(),
                event.getTotalAmount().longValue()));
        log.info("OrderCreatedEvent in Saga for Order Id : {}",
                event.getId());
    }

    @SagaEventHandler(associationProperty = "paymentId")
    void on(PaymentCompletedEvent event) {
        log.info("Complete order command for order : {}", event.getOrderId());
        CompleteOrderCommand cmd = new CompleteOrderCommand(event.getOrderId(), Status.COMPLETED);
        commandGateway.send(cmd);
    }

    @SagaEventHandler(associationProperty = "paymentId")
    void on(PaymentFailedEvent event) {
        CancelOrderCommand cmd = new CancelOrderCommand(event.getOrderId(), event.getBuyerId());
        commandGateway.send(cmd);
    }

    @SagaEventHandler(associationProperty = "id")
    void on(OrderCancelledEvent event) {
        log.info("Order Cancelled with id : {}", event.getId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    void on(OrderCompletedEvent event) {
        orderCommandHandler.sendUpdateInventoryCommand(event.getId());
        log.info("Sage Completed for order : {}", event.getId());
    }
}
