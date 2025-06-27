package org.torres.backendkitchen.Domain.DTO.Dishes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.torres.backendkitchen.Domain.Enum.DishCategory;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private DishCategory category;
    private Boolean isAvailable;
    private String imageUrl;
}
