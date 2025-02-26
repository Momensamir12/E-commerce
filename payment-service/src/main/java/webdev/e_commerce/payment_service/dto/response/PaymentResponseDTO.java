package webdev.e_commerce.payment_service.dto.response;

import webdev.e_commerce.payment_service.model.enums.PaymentStatus;
import webdev.e_commerce.payment_service.model.enums.Provider;

import java.math.BigDecimal;

public record PaymentResponseDTO(
        String orderId,
        String buyerId,
        BigDecimal amount,
        Provider provider,
        String currency,
        PaymentStatus status
) {
}
