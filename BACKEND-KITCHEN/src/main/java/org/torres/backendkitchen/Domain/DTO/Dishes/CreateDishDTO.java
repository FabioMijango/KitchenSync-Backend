package org.torres.backendkitchen.Domain.DTO.Dishes;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.torres.backendkitchen.Domain.Enum.DishCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDishDTO {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private DishCategory category;

    @Column(nullable = false)
    private Boolean isAvailable;

    @Column(nullable = true)
    private String imageUrl;

}
