package webdev.e_commerce.cart_service.exception.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
