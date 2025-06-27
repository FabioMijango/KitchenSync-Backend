package org.torres.backendkitchen.Services.ServiceImplementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.torres.backendkitchen.Domain.DTO.Dishes.CreateDishDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.DishResponseDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.DishSummaryDTO;
import org.torres.backendkitchen.Domain.DTO.Dishes.UpdateDishDTO;
import org.torres.backendkitchen.Domain.Entity.Dish;
import org.torres.backendkitchen.Exception.EntitiesException.EntityDuplicatedException;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotFoundException;
import org.torres.backendkitchen.Repository.iDishRepository;
import org.torres.backendkitchen.Repository.iOrderDishRepository;
import org.torres.backendkitchen.Services.iDishService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements iDishService {
    private final iDishRepository dishRepository;
    private final ModelMapper modelMapper;
    private final iOrderDishRepository orderDishRepository;

    @Override
    public DishResponseDTO getDishByName(String name) {
        Dish dish = dishRepository.findByName(name);
        if (dish == null) {
            throw new EntityNotFoundException("Dish not found with name: " + name);
        }
        return modelMapper.map(dish, DishResponseDTO.class);
    }

    @Override
    public List<DishSummaryDTO> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        List<DishSummaryDTO> dishDTOs = new ArrayList<>();
        for (Dish dish : dishes) {
            dishDTOs.add(modelMapper.map(dish, DishSummaryDTO.class));
        }
        return dishDTOs;
    }


    @Override
    public List<CreateDishDTO> createDish(List<CreateDishDTO> dtoList){
        List<CreateDishDTO> createdDishes = new ArrayList<>();

        for (CreateDishDTO dto : dtoList) {
            if (dishRepository.findByName(dto.getName()) != null) {
                throw new EntityDuplicatedException("El plato '" + dto.getName() + "' ya existe");
            }

            // Convertimos el DTO recibido (CreateDishDTO) a la entidad Dish para poder guardarla en la base de datos
            Dish dish = modelMapper.map(dto, Dish.class);

            // Guardamos la entidad Dish en la base de datos y obtenemos la entidad guardada
            Dish saved = dishRepository.save(dish);

            // Convertimos la entidad guardada de vuelta a DTO para devolver al cliente la información final del plato creado
            CreateDishDTO resultDTO = modelMapper.map(saved, CreateDishDTO.class);

            // Agregamos el DTO resultante a la lista de platos creados
            createdDishes.add(resultDTO);

            // ModelMapper
        }

        return createdDishes;
    }


    @Override
    public DishResponseDTO updateDish(UpdateDishDTO dish, UUID id) {
        Dish dishToUpdate = dishRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Dish not found with id: " + id));

        dishToUpdate.setName(dish.getName());
        dishToUpdate.setDescription(dish.getDescription());
        dishToUpdate.setPrice(dish.getPrice());
        dishToUpdate.setCategory(dish.getCategory());
        dishToUpdate.setIsAvailable(dish.getIsAvailable());
        dishToUpdate.setImageUrl(dish.getImageUrl());

        dishRepository.save(dishToUpdate);

        return modelMapper.map(dishToUpdate, DishResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteDish(UUID id) {
        Dish dish = dishRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Dish not found with id: " + id));
        // Eliminamos relaciones con OrderDish
        orderDishRepository.deleteByDishId(id);
        // Eliminamos el plato
        dishRepository.delete(dish);
    }

    @Override
    public DishResponseDTO getDishById(UUID id) {

            Dish dish = dishRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Dish not found with ID: " + id));

            return modelMapper.map(dish, DishResponseDTO.class);
    }

}

