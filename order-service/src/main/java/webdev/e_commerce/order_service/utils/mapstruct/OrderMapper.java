package webdev.e_commerce.order_service.utils.mapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import webdev.e_commerce.common.dto.OrderItemDTO;
import webdev.e_commerce.order_service.commands.CreateOrderCommand;
import webdev.e_commerce.order_service.dto.request.OrderItemRequestDTO;
import webdev.e_commerce.order_service.dto.request.OrderRequestDTO;
import webdev.e_commerce.order_service.dto.response.OrderItemResponseDTO;
import webdev.e_commerce.order_service.dto.response.OrderResponseDTO;
import webdev.e_commerce.order_service.model.Order;
import webdev.e_commerce.order_service.model.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDTO orderToOrderResponseDTO(Order orderRequestDTO);

    Order orderRequestDTOToOrder(OrderRequestDTO orderRequestDTO);

    @Mapping(source = "order.items", target = "orderedItems")
    CreateOrderCommand orderToCreateOrderCommand(Order order);

    List<OrderResponseDTO> ordersToOrdersResponseDTO(List<Order> orders);

    @Mapping(source = "item.order.id", target = "orderId")
    OrderItemResponseDTO orderItemToOrderItemResponseDTO(OrderItem item);

    List<OrderItem> orderItemsRequestDTOToOrderItems(List<OrderItemRequestDTO> items);

    List<OrderItemDTO> orderItemsRequestDTOToOrderItemsDTO(List<OrderItemRequestDTO> items);

    List<OrderItemDTO> orderItemsResponseDTOToOrderItemsDTO(List<OrderItemResponseDTO> items);

    List<OrderItemDTO> orderItemsToOrderItemsDTO(List<OrderItem> orderItems);
}
