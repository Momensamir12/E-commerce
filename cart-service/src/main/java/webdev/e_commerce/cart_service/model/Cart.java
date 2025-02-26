package webdev.e_commerce.cart_service.model;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "Cart")
public class Cart implements Serializable {
    @Id
    private String id;
    @Valid
    private List<CartItem> items;
    private BigDecimal subTotal;
}
