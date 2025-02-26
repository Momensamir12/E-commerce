package webdev.e_commerce.cart_service.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webdev.e_commerce.cart_service.dto.requestdto.CartItemRequestDTO;
import webdev.e_commerce.cart_service.service.CartService;
import webdev.e_commerce.common.APIResponse;

import static webdev.e_commerce.cart_service.utils.api.APIURI.cartURI;
import static webdev.e_commerce.common.api.APIHelperUtil.createUnifiedResponse;

@RestController
@RequestMapping(cartURI)
@AllArgsConstructor
@Tag(name = "Cart Controller", description = "APIs for managing the shopping cart")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Add an item to the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added to cart successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid item details")
    })
    @PostMapping
    public ResponseEntity<APIResponse> addItemToCart(@RequestBody @Valid CartItemRequestDTO cartItemRequest) {
        return createUnifiedResponse(cartService.addItemToCart(cartItemRequest), HttpStatus.OK, "Item added successfully");
    }

    @Operation(summary = "Retrieve the current user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping
    public ResponseEntity<APIResponse> findCart() {
        return createUnifiedResponse(cartService.findUserCart(), HttpStatus.OK);
    }

    @Operation(summary = "Delete an item from the cart by product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed from cart successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found in cart")
    })
    @DeleteMapping("/{product-id}")
    public ResponseEntity<APIResponse> deleteItemFromCart(@PathVariable("product-id") String productId) {
        cartService.deleteItemFromCart(productId);
        return createUnifiedResponse(HttpStatus.OK, "Item removed successfully");
    }

    @Operation(summary = "Clear the entire cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping
    public ResponseEntity<APIResponse> deleteCart() {
        cartService.deleteCart();
        return createUnifiedResponse(HttpStatus.OK, "Cart cleared successfully");
    }
}
