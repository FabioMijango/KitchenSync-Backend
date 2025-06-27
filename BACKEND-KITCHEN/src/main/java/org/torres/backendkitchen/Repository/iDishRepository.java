package org.torres.backendkitchen.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.torres.backendkitchen.Domain.Entity.Dish;
import org.torres.backendkitchen.Domain.Enum.DishCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iDishRepository extends JpaRepository<Dish, UUID> {
    Optional<Dish> findById(UUID id);

    List<Dish> findAll();


    Dish findByName(String name);
}
