package webdev.e_commerce.cart_service.exception.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
