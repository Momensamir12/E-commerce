package webdev.e_commerce.order_service.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Order_items")
@Data
public class OrderItem extends BaseEntity {

    @Column(name = "product_id", nullable = false)
    private String productId;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
