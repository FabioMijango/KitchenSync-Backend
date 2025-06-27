package org.torres.backendkitchen.Domain.DTO.Dishes;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.torres.backendkitchen.Domain.Enum.DishCategory;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishDTO {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Double price;

    private DishCategory category;

    private Boolean isAvailable;

    @NotNull
    @NotBlank
    private String imageUrl;

}
