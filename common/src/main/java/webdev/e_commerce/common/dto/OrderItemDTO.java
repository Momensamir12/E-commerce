package webdev.e_commerce.common.dto;

import java.math.BigDecimal;

public record OrderItemDTO(
        String productId,
        BigDecimal price,
        Integer quantity
) {
}
