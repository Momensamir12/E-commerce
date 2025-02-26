package webdev.e_commerce.cart_service.service;

import webdev.e_commerce.cart_service.dto.requestdto.CartItemRequestDTO;
import webdev.e_commerce.cart_service.dto.responsedto.CartItemResponseDTO;
import webdev.e_commerce.cart_service.dto.responsedto.CartResponseDTO;
import webdev.e_commerce.cart_service.model.Cart;

import java.math.BigDecimal;


//Remove cart from name of functions
public interface CartService {

    CartItemResponseDTO addItemToCart(CartItemRequestDTO item);

    CartResponseDTO findUserCart();

    Cart findUserCartUtil();

    Cart findByUserId(String id);

    void deleteItemFromCart(String id);

    void deleteCart();
    void deleteCart(String id);
    void deleteItemFromCarts(String id);

    void updateItemPrice(String id, BigDecimal price);


}
