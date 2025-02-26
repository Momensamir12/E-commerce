package webdev.e_commerce.cart_service.utils;

import org.mapstruct.Mapper;
import webdev.e_commerce.cart_service.dto.requestdto.CartItemRequestDTO;
import webdev.e_commerce.cart_service.dto.responsedto.CartItemResponseDTO;
import webdev.e_commerce.cart_service.dto.responsedto.CartResponseDTO;
import webdev.e_commerce.cart_service.model.Cart;
import webdev.e_commerce.cart_service.model.CartItem;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartItem cartItemRequestToCartItem(CartItemRequestDTO item);

    CartItemResponseDTO cartItemToCartItemResponseDTO(CartItem item);

    CartResponseDTO cartToCartResponseDTO(Cart cart);

}
