package webdev.e_commerce.order_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
        BigDecimal subTotal,
        @NotNull String address,
        @Valid List<OrderItemRequestDTO> items
) {
}
