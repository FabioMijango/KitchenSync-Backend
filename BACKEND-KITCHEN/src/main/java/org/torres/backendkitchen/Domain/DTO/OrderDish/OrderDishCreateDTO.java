package org.torres.backendkitchen.Domain.DTO.OrderDish;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.torres.backendkitchen.Domain.Entity.OrderDish;

import java.util.UUID;

@Data
public class OrderDishCreateDTO {
    @NotNull
    @NotBlank
    @NotEmpty
    private UUID orderId;

    private UUID dishId;

    private Integer quantity;
}
