package ru.liga.commons.status;

public enum StatusOrders {
    CUSTOMER_CREATED,
    CUSTOMER_PAID,
    KITCHEN_ACCEPTED,
    KITCHEN_PREPARING,
    DELIVERY_PENDING,
    DELIVERY_PICKING,
    DELIVERY_DELIVERING,
    DELIVERY_COMPLETE,
    CUSTOMER_CANCELLED,
    KITCHEN_DENIED,
    KITCHEN_REFUNDED,
    DELIVERY_DENIED,
    DELIVERY_REFUNDED;

    private static final StatusOrders[] vals = values();

    public StatusOrders next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

}
