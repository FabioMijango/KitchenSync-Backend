package org.torres.backendkitchen.Domain.DTO.OrderDish;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishSummaryDTO {
    UUID orderId;
    UUID dishId;
    Integer waiterId;
    String firstName;
    String name;
    Integer quantity;
    String state;
}