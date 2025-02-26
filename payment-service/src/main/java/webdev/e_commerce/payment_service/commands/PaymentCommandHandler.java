package webdev.e_commerce.payment_service.commands;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import webdev.e_commerce.payment_service.model.Payment;
import webdev.e_commerce.payment_service.model.enums.PaymentStatus;

@Component
@AllArgsConstructor
@Slf4j
public class PaymentCommandHandler {
    private transient CommandGateway commandGateway;

    public void paymentStatusHandler(Payment payment) {
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            commandGateway.send(new CompletePaymentCommand(payment.getId().toString(), payment.getOrderId(), payment.getBuyerId()));
        } else if (payment.getStatus() == PaymentStatus.FAILED) {
            commandGateway.send(new CancelPaymentCommand(payment.getId().toString(), payment.getOrderId(), payment.getBuyerId()));
        }
    }
}
