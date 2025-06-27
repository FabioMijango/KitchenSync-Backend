package org.torres.backendkitchen.Domain.DTO.OrderDish;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDishUpdateDTO {

@NotNull
private String state;

@NotNull
private Integer quantity;

}
