package webdev.e_commerce.cart_service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @NotNull(message = "Valid product id is needed")
    private String productId;
    @NotNull(message = "Product Price is required")
    private BigDecimal price;
    @NotNull(message = "Product quantity is required")
    private int quantity;
}
