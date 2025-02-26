package webdev.e_commerce.order_service.dto.response;

import webdev.e_commerce.common.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        Status status,
        String address,
        List<OrderItemResponseDTO> items,
        BigDecimal subTotal,
        BigDecimal totalAmount
) {
}
