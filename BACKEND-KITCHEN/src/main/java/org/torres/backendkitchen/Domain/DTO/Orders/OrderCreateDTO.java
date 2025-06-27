package org.torres.backendkitchen.Domain.DTO.Orders;

import jakarta.persistence.*;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateDTO {
    private UUID table;
    private Integer waiterId;
    private OrderState state;
    private Double totalAmount;
    private OrderPaymentMethod paymentMethod;

    private List<OrderDishData> orderDishes;
}
