package webdev.e_commerce.payment_service.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webdev.e_commerce.common.APIResponse;
import webdev.e_commerce.payment_service.dto.request.ProcessPaymentRequestDTO;
import webdev.e_commerce.payment_service.model.enums.PaymentStatus;
import webdev.e_commerce.payment_service.service.PaymentService;
import webdev.e_commerce.payment_service.utils.api.APIURI;

import static webdev.e_commerce.common.api.APIHelperUtil.createUnifiedResponse;

@RestController
@RequestMapping(APIURI.paymentURI)
@AllArgsConstructor
@Tag(name = "Payment Controller", description = "APIs for processing and retrieving payment information")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "Process a payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment request")
    })
    @PostMapping
    public ResponseEntity<APIResponse> process(@RequestBody ProcessPaymentRequestDTO request) {
        return createUnifiedResponse(paymentService.process(request), HttpStatus.ACCEPTED, PaymentStatus.SUCCESS.toString());
    }

    @Operation(summary = "Find payments for the current authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No payments found for the user")
    })
    @GetMapping
    public ResponseEntity<APIResponse> findAll() {
        return createUnifiedResponse(paymentService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Find payments by buyer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No payments found for the specified buyer ID")
    })
    @GetMapping("/buyer-id/{buyer-id}")
    public ResponseEntity<APIResponse> findAllByBuyerId(@PathVariable("buyer-id") String buyerId) {
        return createUnifiedResponse(paymentService.findAll(buyerId), HttpStatus.OK);
    }
}