package webdev.e_commerce.order_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webdev.e_commerce.common.Status;
import webdev.e_commerce.order_service.commands.OrderCommandHandler;
import webdev.e_commerce.order_service.dto.request.OrderRequestDTO;
import webdev.e_commerce.order_service.dto.response.OrderResponseDTO;
import webdev.e_commerce.order_service.model.Order;
import webdev.e_commerce.order_service.model.OrderItem;
import webdev.e_commerce.order_service.repository.OrderRepository;
import webdev.e_commerce.order_service.service.OrderService;
import webdev.e_commerce.order_service.utils.CurrentAuthContext;
import webdev.e_commerce.order_service.utils.mapstruct.OrderMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final BigDecimal SHIPPING_COST = new BigDecimal(100);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderCommandHandler orderCommandHandler;

    @Override
    @Transactional
    public OrderResponseDTO create(@Valid OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        String buyerId = CurrentAuthContext.getUserID();
        String buyerEmail = CurrentAuthContext.getUserEmail();
        if (buyerId == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        order.setBuyerId(buyerId);
        order.setAddress(orderRequestDTO.address());
        order.setStatus(Status.APPROVED);
        order.setCreatedBy(buyerEmail);
        calculateOrderDetails(order, orderRequestDTO);
        Order savedOrder = orderRepository.save(order);
        orderCommandHandler.sendCreateOrderCommand(order);
        return orderMapper.orderToOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO findById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        return orderMapper.orderToOrderResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> findAllByBuyerId(String buyerId) {
        if (buyerId == null)
            throw new IllegalArgumentException("Buyer id is null");
        List<Order> orders = orderRepository.findAllByBuyerId(buyerId);
        if (orders == null || orders.isEmpty())
            throw new EntityNotFoundException("No Orders found for this buyer id");
        return orderMapper.ordersToOrdersResponseDTO(orders);
    }

    @Override
    @Transactional
    public void changeStatus(UUID id, Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID " + id));
        order.setStatus(status);
        orderRepository.save(order);
    }

    private void calculateOrderDetails(Order order, OrderRequestDTO orderRequestDTO) {
        List<OrderItem> items = orderMapper.orderItemsRequestDTOToOrderItems(orderRequestDTO.items());
        BigDecimal subTotal = BigDecimal.ZERO;
        for (OrderItem item : items) {
            item.setOrder(order);
            subTotal = subTotal.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setItems(items);
        order.setSubTotal(subTotal);
        order.setTotalAmount(subTotal.add(SHIPPING_COST));
    }
}
