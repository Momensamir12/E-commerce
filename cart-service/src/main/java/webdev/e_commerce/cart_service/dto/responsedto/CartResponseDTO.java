package webdev.e_commerce.cart_service.dto.responsedto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        String id,
        List<CartItemResponseDTO> items,
        BigDecimal subTotal
) {
}
