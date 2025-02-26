package webdev.e_commerce.order_service.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webdev.e_commerce.common.APIResponse;
import webdev.e_commerce.order_service.dto.request.OrderRequestDTO;
import webdev.e_commerce.order_service.service.OrderService;
import webdev.e_commerce.order_service.utils.CurrentAuthContext;

import static webdev.e_commerce.common.api.APIHelperUtil.createUnifiedResponse;
import static webdev.e_commerce.order_service.utils.api.APIURI.userURI;

@RestController
@RequestMapping(userURI)
@AllArgsConstructor
@Tag(name = "Order Controller", description = "APIs related to user operations in order service")
public class OrderUserController {
    private final OrderService orderService;

    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order details")
    })
    @PostMapping
    public ResponseEntity<APIResponse> createOrder(@RequestBody @Valid OrderRequestDTO order) {
        return createUnifiedResponse(orderService.create(order), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all orders for the current authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No orders found for the user")
    })
    @GetMapping
    public ResponseEntity<APIResponse> findAll() {
        return createUnifiedResponse(orderService.findAllByBuyerId(CurrentAuthContext.getUserID()), HttpStatus.OK);
    }
}

