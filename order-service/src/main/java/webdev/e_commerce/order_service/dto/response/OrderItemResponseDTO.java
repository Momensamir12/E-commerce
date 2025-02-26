package webdev.e_commerce.order_service.dto.response;

import java.util.UUID;

public record OrderItemResponseDTO(
        UUID id,
        UUID orderId,
        String productId,
        Integer quantity
) {
}
