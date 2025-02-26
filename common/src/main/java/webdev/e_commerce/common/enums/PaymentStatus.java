package webdev.e_commerce.common.enums;

public enum PaymentStatus {
    CREATED("Payment in processing"),
    SUCCESS("Payment success"),
    FAILED("Payment failed");
    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
