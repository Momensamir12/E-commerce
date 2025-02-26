package webdev.e_commerce.common;

public enum Status {
    APPROVED("Order approved"),
    COMPLETED("Order completed"),
    CANCELLED("Order cancelled");
    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
