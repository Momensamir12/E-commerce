package webdev.e_commerce.cart_service.dto.requestdto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CartItemRequestDTO(
        @NotNull
        String productId,
        @NotNull
        Integer quantity,
        @NotNull
        BigDecimal price
) {
}
