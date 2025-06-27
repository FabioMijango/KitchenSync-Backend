package org.torres.backendkitchen.Domain.DTO.Orders;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDishData {
    @NotNull
    private UUID dishId;
    @NotNull
    private Integer quantity;
}
