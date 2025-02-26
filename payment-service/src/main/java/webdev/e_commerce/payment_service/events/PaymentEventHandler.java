package webdev.e_commerce.payment_service.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import webdev.e_commerce.common.events.payment.ProcessingPaymentEvent;
import webdev.e_commerce.payment_service.service.PaymentService;

@Component
@AllArgsConstructor
@Slf4j
public class PaymentEventHandler {
    private final PaymentService paymentService;

    @EventHandler
    void on(ProcessingPaymentEvent event) {
        paymentService.init(event);
    }
}
