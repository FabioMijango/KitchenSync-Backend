package org.torres.backendkitchen.Domain.DTO.Orders;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.torres.backendkitchen.Domain.Entity.Tables;
import org.torres.backendkitchen.Domain.Entity.User;
import org.torres.backendkitchen.Domain.Enum.OrderPaymentMethod;
import org.torres.backendkitchen.Domain.Enum.OrderState;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateDTO {
    @NotNull
    private UUID table;
    private Integer waiterId;
    @NotNull
    private OrderState state;
    @NotNull
    private Double totalAmount;
    @NotNull
    private OrderPaymentMethod paymentMethod;
    private List<OrderDishData> orderDishes;
}
