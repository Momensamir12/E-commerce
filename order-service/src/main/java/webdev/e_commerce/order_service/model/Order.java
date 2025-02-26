package webdev.e_commerce.order_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webdev.e_commerce.common.Status;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order extends BaseEntityAudited {
    @NotNull(message = "Buyer id must be provided")
    @Column(name = "buyer_id")
    private String buyerId;
    private Status status;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "sub_total")
    private BigDecimal subTotal;
    @Column(name = "address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> items;
}
