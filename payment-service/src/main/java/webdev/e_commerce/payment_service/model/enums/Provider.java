package webdev.e_commerce.payment_service.model.enums;

public enum Provider {
    STRIPE("Stripe");
    private final String value;
    Provider(String value) {
        this.value = value;
    }
    public String toString() {
        return value;
    }
}
