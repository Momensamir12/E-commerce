package webdev.e_commerce.common.enums;

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
