package org.torres.backendkitchen.Controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.torres.backendkitchen.Domain.DTO.OrderDish.OrderDishCreateDTO;
import org.torres.backendkitchen.Domain.DTO.OrderDish.OrderDishSummaryDTO;
import org.torres.backendkitchen.Domain.DTO.OrderDish.OrderDishUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.OrderDish;
import org.torres.backendkitchen.Services.iOrderDishService;
import org.torres.backendkitchen.Services.iOrderService;
import org.torres.backendkitchen.util.GenericResponse;

import java.util.List;
import java.util.UUID;

import static org.torres.backendkitchen.util.Constants.*;

@RestController
@RequestMapping(KITCHEN+ORDER_DISH_CONTROLLER)
@RequiredArgsConstructor
public class OrderDishController {

    private final iOrderDishService orderDishService;
    @PostMapping
    public ResponseEntity<GenericResponse> createOrderDish(@RequestBody OrderDishCreateDTO orderDishDTO) {
        orderDishService.createOrderDish(orderDishDTO);

        return GenericResponse.builder()
                .message("Order Dish created successfully")
                .data(null)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenericResponse> updateOrderDish(@PathVariable UUID id,
                                                           @RequestBody OrderDishUpdateDTO orderDishDTO) {

        orderDishService.updateOrderDishState(orderDishDTO, id);

        return GenericResponse.builder()
                .message("Order Dish updated successfully")
                .data(null)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping(DELETE+"/{id}")
    public ResponseEntity<GenericResponse> deleteOrderDish(@PathVariable UUID id) {
        orderDishService.deleteOrderDish(id);
        return GenericResponse
                .builder()
                .message("Order Dish deleted successfully")
                .data(null)
                .status(HttpStatus.OK)
                .build()
                .buildResponse();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping(DELETE+ORDER_ID)
    public ResponseEntity<GenericResponse> deleteAllOrderDishesByOrderId(@RequestParam UUID orderId) {
        orderDishService.deleteAllOrderDishesByOrderId(orderId);
        return GenericResponse
                .builder()
                .message("All Order Dishes for Order ID " + orderId + " deleted successfully")
                .data(null)
                .status(HttpStatus.OK)
                .build()
                .buildResponse();
    }

    @GetMapping(ORDER_ID)
    public ResponseEntity<GenericResponse> findAllByOrderId(@RequestParam UUID orderId) {
        List<OrderDish> orderDishes = orderDishService.findAllByOrderId(orderId);

        List<OrderDishSummaryDTO> dtos = orderDishes.stream()
                .map(orderDish -> {
                    OrderDishSummaryDTO.OrderDishSummaryDTOBuilder builder = OrderDishSummaryDTO.builder()
                            .orderId(orderDish.getOrder().getId())
                            .dishId(orderDish.getDish().getId())
                            .name(orderDish.getDish().getName())
                            .quantity(orderDish.getQuantity())
                            .state(orderDish.getState().toString());

                    if (orderDish.getOrder().getWaiterId() != null) {
                        builder.waiterId(orderDish.getOrder().getWaiterId().getUserId());
                        builder.firstName(orderDish.getOrder().getWaiterId().getFirstName());
                        builder.name(orderDish.getDish().getName());
                    }

                    return builder.build();
                })
                .toList();

        return GenericResponse.builder()
                .message("Order Dishes found successfully")
                .data(dtos)
                .status(HttpStatus.OK)
                .build()
                .buildResponse();
    }
    @GetMapping(GET_BY_ID)
    public ResponseEntity<GenericResponse> findById(@RequestParam UUID id) {
        OrderDish orderDish = orderDishService.findById(id);

        OrderDishSummaryDTO.OrderDishSummaryDTOBuilder builder = OrderDishSummaryDTO.builder()
                .orderId(orderDish.getOrder().getId())
                .dishId(orderDish.getDish().getId())
                .name(orderDish.getDish().getName())
                .quantity(orderDish.getQuantity())
                .state(orderDish.getState().toString());

        if (orderDish.getOrder().getWaiterId() != null) {
            builder.waiterId(orderDish.getOrder().getWaiterId().getUserId());
            builder.firstName(orderDish.getOrder().getWaiterId().getFirstName());
        }

        OrderDishSummaryDTO dto = builder.build();
        
        return GenericResponse.builder()
                .message("Order Dish found successfully")
                .data(dto)
                .status(HttpStatus.OK)
                .build()
                .buildResponse();
    }
}

