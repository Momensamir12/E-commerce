package webdev.e_commerce.order_service.commands;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import webdev.e_commerce.common.commands.UpdateInventoryCommand;
import webdev.e_commerce.common.dto.OrderItemDTO;
import webdev.e_commerce.order_service.model.Order;
import webdev.e_commerce.order_service.repository.OrderRepository;
import webdev.e_commerce.order_service.utils.mapstruct.OrderMapper;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class OrderCommandHandler {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private transient CommandGateway commandGateway;

    public void sendCreateOrderCommand(Order order) {
        CreateOrderCommand cmd = orderMapper.orderToCreateOrderCommand(order);
        commandGateway.send(cmd);
    }

    public void sendUpdateInventoryCommand(String orderId) {
        Order order = orderRepository.findById(UUID.fromString(orderId)).
                orElseThrow(() -> new EntityNotFoundException("Order not found for ID" + orderId));
        List<OrderItemDTO> items = orderMapper.orderItemsToOrderItemsDTO(order.getItems());
        UpdateInventoryCommand cmd = new UpdateInventoryCommand(UUID.randomUUID().toString(), items);
        commandGateway.send(cmd);
        log.info("Updating inventory for order " + orderId);
    }
}
