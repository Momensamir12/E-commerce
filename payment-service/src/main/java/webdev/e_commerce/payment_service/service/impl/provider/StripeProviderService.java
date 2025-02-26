package webdev.e_commerce.payment_service.service.impl.provider;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import webdev.e_commerce.payment_service.dto.request.ProcessPaymentRequestDTO;
import webdev.e_commerce.payment_service.dto.response.ProcessPaymentResponseDTO;
import webdev.e_commerce.payment_service.model.enums.PaymentStatus;
import webdev.e_commerce.payment_service.service.PaymentProviderService;

@Service
@Slf4j
public class StripeProviderService implements PaymentProviderService {
    @Value("${stripe.secretKey}")
    private String secretKey;

    @Override
    public ProcessPaymentResponseDTO process(ProcessPaymentRequestDTO request) {
        Stripe.apiKey = secretKey;
        PaymentStatus status = PaymentStatus.CREATED;
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("test")
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(request.currency() != null ? request.currency() : "USD")
                        .setUnitAmount(request.amount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:2026/success")
                        .setCancelUrl("http://localhost:2026/cancel")
                        .addLineItem(lineItem)
                        .build();
        Session session = null;
        status = PaymentStatus.SUCCESS;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            status = PaymentStatus.FAILED;
        }

        return new ProcessPaymentResponseDTO(request.orderId(), request.buyerId(), request.currency(), request.amount(),
                status, "Payment processed", session.getId(), session.getUrl());
    }
}
