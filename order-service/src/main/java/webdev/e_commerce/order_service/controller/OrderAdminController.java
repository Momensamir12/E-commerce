package webdev.e_commerce.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webdev.e_commerce.common.APIResponse;
import webdev.e_commerce.order_service.service.OrderService;

import static webdev.e_commerce.common.api.APIHelperUtil.createUnifiedResponse;
import static webdev.e_commerce.order_service.utils.api.APIURI.adminURI;

@RestController
@RequestMapping(adminURI)
@AllArgsConstructor
@Tag(name = "Order Admin Controller", description = "APIs related to admin operations in order service")
public class OrderAdminController {
    private final OrderService orderService;

    @Operation(summary = "Get all orders by a specific user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No orders found for the specified user ID")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<APIResponse> findAllById(@PathVariable("id") String id) {
        return createUnifiedResponse(orderService.findAllByBuyerId(id), HttpStatus.OK);
    }
}
