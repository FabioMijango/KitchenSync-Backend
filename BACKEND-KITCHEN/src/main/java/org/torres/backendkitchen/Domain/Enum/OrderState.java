package org.torres.backendkitchen.Domain.Enum;

public enum OrderState {
    IN_PROGRESS, // Order is being prepared
    READY_TO_SERVE, // Order is ready for pickup or delivery
    COMPLETED, // Order has been completed and served
    CANCELLED // Order has been cancelled
}
