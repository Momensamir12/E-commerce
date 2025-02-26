package webdev.e_commerce.payment_service.aggregate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import webdev.e_commerce.common.commands.CreatePaymentCommand;
import webdev.e_commerce.common.events.payment.PaymentCompletedEvent;
import webdev.e_commerce.common.events.payment.PaymentFailedEvent;
import webdev.e_commerce.common.events.payment.ProcessingPaymentEvent;
import webdev.e_commerce.payment_service.commands.CancelPaymentCommand;
import webdev.e_commerce.payment_service.commands.CompletePaymentCommand;
import webdev.e_commerce.payment_service.model.enums.PaymentStatus;

@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String id;
    private Long amount;
    private String orderId;
    private String buyerId;
    private PaymentStatus status;

    @CommandHandler
    public PaymentAggregate(CreatePaymentCommand cmd) {
        ProcessingPaymentEvent event = new ProcessingPaymentEvent(cmd.getId(), cmd.getOrderId(), cmd.getBuyerId(), cmd.getAmount());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    void on(ProcessingPaymentEvent event) {
        this.id = event.getPaymentId();
        this.buyerId = event.getBuyerId();
        this.orderId = event.getOrderId();
        this.amount = event.getAmount();
        this.status = PaymentStatus.CREATED;
        log.info("Payment created with id : " + event.getPaymentId() + " for order id : " + orderId);
    }

    @CommandHandler
    void handle(CancelPaymentCommand cmd) {
        PaymentFailedEvent event = new PaymentFailedEvent(cmd.getId(), cmd.getOrderId(), cmd.getBuyerId());
        AggregateLifecycle.apply(cmd);
    }

    @CommandHandler
    void handle(CompletePaymentCommand cmd) {
        PaymentCompletedEvent event = new PaymentCompletedEvent(cmd.getId(), cmd.getOrderId(), cmd.getBuyerId());
        AggregateLifecycle.apply(event);
        log.info("Payment completed with id " + id + "for order id" + event.getOrderId());
    }
}
