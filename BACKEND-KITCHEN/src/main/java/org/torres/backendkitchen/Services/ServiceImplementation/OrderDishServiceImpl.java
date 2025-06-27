package org.torres.backendkitchen.Services.ServiceImplementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.torres.backendkitchen.Domain.DTO.OrderDish.OrderDishCreateDTO;
import org.torres.backendkitchen.Domain.DTO.OrderDish.OrderDishUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.Dish;
import org.torres.backendkitchen.Domain.Entity.Order;
import org.torres.backendkitchen.Domain.Entity.OrderDish;
import org.torres.backendkitchen.Domain.Enum.OrderDishState;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotFoundException;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotValidException;
import org.torres.backendkitchen.Repository.iDishRepository;
import org.torres.backendkitchen.Repository.iOrderDishRepository;
import org.torres.backendkitchen.Repository.iOrderRepository;
import org.torres.backendkitchen.Services.iOrderDishService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDishServiceImpl implements iOrderDishService {

    private final iOrderDishRepository orderDishRepository;
    private final iOrderRepository orderRepository;
    private final iDishRepository dishRepository;


    @Override
    public void createOrderDish(OrderDishCreateDTO orderDishDTO) {
        Order order = orderRepository.findById(orderDishDTO.getOrderId()).orElseThrow(
                () -> new EntityNotFoundException("Order Not Found"));

        Dish dish = dishRepository.findById(orderDishDTO.getDishId()).orElseThrow(
                () -> new EntityNotFoundException("Dish Not Found"));

        OrderDish orderDish = OrderDish.builder()
                .order(order)
                .dish(dish)
                .state(OrderDishState.IN_PROGRESS)
                .quantity(orderDishDTO.getQuantity())
                .build();

        orderDishRepository.save(orderDish);

    }

    @Override
    public void updateOrderDishState(OrderDishUpdateDTO orderDishDTO, UUID orderDishId) {

        OrderDish orderDish = orderDishRepository.findById(orderDishId).orElseThrow(
                () -> new EntityNotFoundException("Order Dish Not Found")
        );

        if (orderDishDTO.getState() == null && orderDishDTO.getQuantity() == null) {
            throw new EntityNotValidException("At least one field must be provided for update");
        }

        if(orderDishDTO.getState() != null) {
            orderDish.setState(OrderDishState.valueOf(orderDishDTO.getState()));
        }
        if(orderDishDTO.getQuantity() != null) {
            orderDish.setQuantity(orderDishDTO.getQuantity());
        }

        orderDishRepository.save(orderDish);

    }

    @Override
    public void deleteOrderDish(UUID orderDishId) {
        OrderDish orderDish = orderDishRepository.findById(orderDishId).orElseThrow(
                () -> new EntityNotFoundException("Order Dish Not Found")
        );

        orderDishRepository.delete(orderDish);
    }

    @Override
    public void deleteAllOrderDishesByOrderId(UUID orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order Not Found")
        );

        List<OrderDish> orderDishes = orderDishRepository.findAllByOrder(order);
        if (orderDishes.isEmpty()) {
            throw new EntityNotFoundException("No Order Dishes found for this Order");
        }

        orderDishRepository.deleteAll(orderDishes);
    }

    @Override
    public List<OrderDish> findAllByOrderId(UUID orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order Not Found")
        );

        return orderDishRepository.findAllByOrder(order);
    }

    @Override
    public OrderDish findById(UUID orderDishId) {
        return orderDishRepository.findById(orderDishId).orElseThrow(
                () -> new EntityNotFoundException("Order Dish Not Found")
        );
    }
}
