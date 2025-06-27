package org.torres.backendkitchen.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.torres.backendkitchen.Domain.DTO.Dishes.CreateDishDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.DishResponseDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.DishSummaryDTO;
import org.torres.backendkitchen.Domain.DTO.GenericResponse;
import org.torres.backendkitchen.Domain.DTO.Dishes.UpdateDishDTO;
import org.torres.backendkitchen.Domain.Entity.Dish;
import org.torres.backendkitchen.Domain.Enum.DishCategory;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotFoundException;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotValidException;
import org.torres.backendkitchen.Services.iDishService;

import java.util.List;
import java.util.UUID;

import static org.torres.backendkitchen.util.Constants.*;

@RestController
@RequestMapping(KITCHEN + DISH_CONTROLLER)
@RequiredArgsConstructor
public class DishController {
    private final iDishService dishService;

    @GetMapping(GET_BY_NAME)
    public ResponseEntity<GenericResponse> getDishByName(@RequestParam("name") String name) {
        try {
            DishResponseDTO data = dishService.getDishByName(name);
            return GenericResponse.builder()
                    .message("DishFound")
                    .data(data)
                    .status(HttpStatus.OK)
                    .build()
                    .buildResponse();
        } catch (Exception e) {
            return GenericResponse.builder()
                    .message("Error: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
                    .buildResponse();
        }

    }


    @GetMapping(GET_ALL)
    public  ResponseEntity<GenericResponse> getAllDishes() {
        List<DishSummaryDTO> data = dishService.getAllDishes();
        return GenericResponse.builder()
                .message("DishesFound")
                .data(data)
                .status(HttpStatus.OK)
                .build()
                .buildResponse();
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping(CREATE)
    public ResponseEntity<GenericResponse> createDish(@RequestBody @Valid List<CreateDishDTO> object) {

        List<CreateDishDTO> data = dishService.createDish(object);

        return GenericResponse.builder()
                .message("DishCreated")
                .data(data)
                .status(HttpStatus.OK)
                .build()
                .buildResponse();

    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping(UPDATE)
    public ResponseEntity<GenericResponse> updateDish(@RequestBody @Valid UpdateDishDTO object, @RequestParam("id") UUID id) {
        if (id == null) {
            throw new EntityNotValidException("No se encontró el id original del plato a actualizar");
        }
        return GenericResponse.builder()
                .message("DishUpdated")
                .data(dishService.updateDish(object, id))
                .status(HttpStatus.OK)
                .build()
                .buildResponse();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping(DELETE)
    public ResponseEntity<GenericResponse> deleteDish(@RequestParam("id") UUID id) {
        try {
            dishService.deleteDish(id);

            return GenericResponse.builder()
                    .message("El platillo fue eliminado exitosamente.")
                    .status(HttpStatus.OK)
                    .build()
                    .buildResponse();

        } catch (EntityNotFoundException e) {
            return GenericResponse.builder()
                    .message("Error: Plato no encontrado con ID: " + id)
                    .status(HttpStatus.NOT_FOUND)
                    .build()
                    .buildResponse();

        } catch (Exception e) {
            return GenericResponse.builder()
                    .message("Error inesperado: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build()
                    .buildResponse();
        }
    }



}
