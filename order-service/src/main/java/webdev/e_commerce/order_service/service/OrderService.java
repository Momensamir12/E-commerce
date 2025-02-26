package webdev.e_commerce.order_service.service;


import webdev.e_commerce.common.Status;
import webdev.e_commerce.order_service.dto.request.OrderRequestDTO;
import webdev.e_commerce.order_service.dto.response.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO findById(UUID id);

    List<OrderResponseDTO> findAllByBuyerId(String buyerId);

    void changeStatus(UUID id, Status status);
}