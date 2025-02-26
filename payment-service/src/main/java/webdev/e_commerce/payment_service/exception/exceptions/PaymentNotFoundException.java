package webdev.e_commerce.payment_service.exception.exceptions;


public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
