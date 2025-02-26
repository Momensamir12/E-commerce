package webdev.e_commerce.payment_service.dto.response;

import webdev.e_commerce.payment_service.model.enums.PaymentStatus;

public record ProcessPaymentResponseDTO(
        String orderId,
        String buyerId,
        String currency,
        Long amount,
        PaymentStatus paymentStatus,
        String message,
        String sessionId,
        String sessionUrl
) {
}
