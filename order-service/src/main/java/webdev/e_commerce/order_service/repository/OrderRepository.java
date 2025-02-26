package webdev.e_commerce.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webdev.e_commerce.order_service.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByBuyerId(String buyerId);
}
