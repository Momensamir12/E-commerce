package webdev.e_commerce.payment_service.dto.request;

import webdev.e_commerce.payment_service.model.enums.Provider;

public record ProcessPaymentRequestDTO(
        String orderId,
        String buyerId,
        String currency,
        Provider provider,
        Long amount
) {
}
