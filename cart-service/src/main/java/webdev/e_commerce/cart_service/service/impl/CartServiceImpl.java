package webdev.e_commerce.cart_service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webdev.e_commerce.cart_service.dto.requestdto.CartItemRequestDTO;
import webdev.e_commerce.cart_service.dto.responsedto.CartItemResponseDTO;
import webdev.e_commerce.cart_service.dto.responsedto.CartResponseDTO;
import webdev.e_commerce.cart_service.exception.exceptions.CartItemNotFoundException;
import webdev.e_commerce.cart_service.exception.exceptions.CartNotFoundException;
import webdev.e_commerce.cart_service.model.Cart;
import webdev.e_commerce.cart_service.model.CartItem;
import webdev.e_commerce.cart_service.repository.CartRepository;
import webdev.e_commerce.cart_service.service.CartService;
import webdev.e_commerce.cart_service.utils.CartMapper;
import webdev.e_commerce.cart_service.utils.CurrentAuthContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static webdev.e_commerce.cart_service.exception.ErrorMessages.CART_ITEM_NOT_FOUND_ERROR;
import static webdev.e_commerce.cart_service.exception.ErrorMessages.CART_NOT_FOUND_ERROR;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartItemResponseDTO addItemToCart(CartItemRequestDTO item) {
        Cart cart = findUserCartUtil();
        cart.getItems().add(cartMapper.cartItemRequestToCartItem(item));
        cart.setSubTotal(cart.getSubTotal().add(item.price().multiply(new BigDecimal(item.quantity()))));
        cartRepository.save(cart);
        return cartMapper.cartItemToCartItemResponseDTO(cartMapper.cartItemRequestToCartItem(item));
    }

    @Override
    public CartResponseDTO findUserCart() {
        return cartMapper.cartToCartResponseDTO(findUserCartUtil());
    }

    @Override
    @Transactional
    public void deleteItemFromCart(String productId) {
        Cart cart = findUserCartUtil();
        CartItem item = cart.getItems().stream().filter(item1 -> item1.getProductId().equals(productId)).findFirst()
                .orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND_ERROR));
        item.setQuantity(item.getQuantity() - 1);
        cart.setSubTotal(cart.getSubTotal().subtract(item.getPrice()));
        if (item.getQuantity() == 0)
            cart.getItems().remove(item);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCart() {
        Cart cart = findUserCartUtil();
        if (cart.getItems() == null)
            return;
        cart.getItems().clear();
        cart.setSubTotal(new BigDecimal(0));
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(String id) {
        Cart cart = findByUserId(id);
        if (cart.getItems() == null)
            return;
        cart.getItems().clear();
        cart.setSubTotal(new BigDecimal(0));
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart findUserCartUtil() {
        String userId = CurrentAuthContext.getUserID();
        if (userId == null)
            throw new IllegalStateException("User isn't authenticated");
        Cart cart = cartRepository.findById(userId).orElse(new Cart());
        if (cart.getItems() == null) {
            cart.setId(userId);
            cart.setItems(new ArrayList<>());
            cart.setSubTotal(new BigDecimal(0));
        }
        return cart;
    }

    @Override
    @Transactional
    public void deleteItemFromCarts(String id) {
        List<Cart> carts = (List<Cart>) cartRepository.findAll();
        for (Cart cart : carts) {
            List<CartItem> items = cart.getItems().stream().filter(item1 -> !item1.getProductId().equals(id))
                    .toList();
            cart.setItems(items);
        }
        cartRepository.saveAll(carts);
    }

    @Override
    public Cart findByUserId(String id) {
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND_ERROR));
    }

    @Override
    @Transactional
    public void updateItemPrice(String id, BigDecimal price) {
        List<Cart> carts = (List<Cart>) cartRepository.findAll();
        for (Cart cart : carts) {
            CartItem item = (CartItem) cart.getItems().stream().filter(item1 -> item1.getProductId().equals(id));
            item.setPrice(price);
        }
        cartRepository.saveAll(carts);
    }
}
