package org.torres.backendkitchen.Domain.DTO.Orders;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderSummaryDTO {
    private UUID id;
    private Integer tableNumber;
    private String waiterName;
    private String state;
    private Double totalAmount;
    private String paymentMethod;
}
