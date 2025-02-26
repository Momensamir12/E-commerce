package webdev.e_commerce.order_service.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemRequestDTO(
        @NotNull String productId,
        @NotNull BigDecimal price,
        @NotNull Integer quantity
) {
}
