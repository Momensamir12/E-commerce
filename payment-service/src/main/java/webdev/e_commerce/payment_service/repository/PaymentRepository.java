package webdev.e_commerce.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webdev.e_commerce.payment_service.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByBuyerId(String buyerId);

    Payment findByOrderId(String orderId);
}
