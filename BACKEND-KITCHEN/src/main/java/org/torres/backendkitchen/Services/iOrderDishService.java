package org.torres.backendkitchen.Services;

import org.torres.backendkitchen.Domain.DTO.OrderDish.OrderDishCreateDTO;
import org.torres.backendkitchen.Domain.DTO.OrderDish.OrderDishUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.OrderDish;

import java.util.List;
import java.util.UUID;

public interface iOrderDishService {

    public void createOrderDish(OrderDishCreateDTO orderDishDTO);

    public void updateOrderDishState(OrderDishUpdateDTO orderDishDTO, UUID orderDishId);

    public void deleteOrderDish(UUID orderDishId);

    public void deleteAllOrderDishesByOrderId(UUID orderId);

    public List<OrderDish> findAllByOrderId(UUID orderId);

    public OrderDish findById(UUID orderDishId);



}
