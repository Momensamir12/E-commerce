package webdev.e_commerce.cart_service.dto.responsedto;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        String productId,
        BigDecimal price,
        Integer quantity
) {
}
