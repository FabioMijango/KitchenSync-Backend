package org.torres.backendkitchen.Domain.DTO.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.torres.backendkitchen.Domain.Entity.Tables;
import org.torres.backendkitchen.Domain.Entity.User;
import org.torres.backendkitchen.Domain.Enum.OrderPaymentMethod;
import org.torres.backendkitchen.Domain.Enum.OrderState;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Tables table;
    private User waiterId;
    private OrderState state;
    private Double totalAmount;
    private OrderPaymentMethod paymentMethod;
}
