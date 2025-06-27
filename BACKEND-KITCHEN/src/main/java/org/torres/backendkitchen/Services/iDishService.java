package org.torres.backendkitchen.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.torres.backendkitchen.Domain.DTO.Dishes.CreateDishDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.DishResponseDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.DishSummaryDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.UpdateDishDTO;
import org.torres.backendkitchen.Domain.Entity.Dish;
import org.torres.backendkitchen.Domain.Enum.DishCategory;

import java.util.List;
import java.util.UUID;

public interface iDishService {
    DishResponseDTO getDishByName(String name);
    List<DishSummaryDTO> getAllDishes();
    List<CreateDishDTO> createDish(List<CreateDishDTO> dtos);
    DishResponseDTO updateDish(UpdateDishDTO dish,UUID ID);
    void deleteDish(UUID id);

    DishResponseDTO getDishById(UUID id);
}
