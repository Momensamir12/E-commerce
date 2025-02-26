package webdev.e_commerce.payment_service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webdev.e_commerce.payment_service.model.enums.PaymentStatus;
import webdev.e_commerce.payment_service.model.enums.Provider;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment extends BaseEntity {
    @Column(name = "buyer_id", nullable = false)
    private String buyerId;
    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;
    @Column(name = "provider")
    private Provider provider;
    @Column(name = "amount", nullable = false)
    private Long amount;
    @Column(name = "currency")
    private String currency;
    @Column(name = "status", nullable = false)
    private PaymentStatus status;
}
