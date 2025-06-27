package org.torres.backendkitchen.Services.ServiceImplementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderCreateDTO;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderDishData;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderSummaryDTO;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.*;
import org.torres.backendkitchen.Domain.Enum.OrderDishState;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotFoundException;
import org.torres.backendkitchen.Repository.*;
import org.torres.backendkitchen.Domain.Enum.OrderPaymentMethod;
import org.torres.backendkitchen.Domain.Enum.OrderState;
import org.torres.backendkitchen.Exception.ExceptionUser.UserLoginException;
import org.torres.backendkitchen.Services.iOrderService;

import java.util.List;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements iOrderService {
    private final iOrderRepository orderRepository;
    private final iTableRepository tableRepository;
    private final iUserRepository userRepository;
    private final iDishRepository dishRepository;
    private final iOrderDishRepository orderDishRepository;
    private final ModelMapper modelMapper;

@Override
public void createOrder(OrderCreateDTO orderDTO) {
    Order order = new Order();
    order.setState(orderDTO.getState());
    order.setTotalAmount(orderDTO.getTotalAmount());
    order.setPaymentMethod(orderDTO.getPaymentMethod());

    Tables table = tableRepository.findById(orderDTO.getTable())
        .orElseThrow(() -> new EntityNotFoundException("Table not found"));
    User waiter = userRepository.findById(orderDTO.getWaiterId())
        .orElseThrow(() -> new EntityNotFoundException("Waiter not found"));

    order.setTable(table);
    order.setWaiterId(waiter);

    orderRepository.save(order);

    List<OrderDishData> orderDishes = orderDTO.getOrderDishes();
    if(orderDishes != null && !orderDishes.isEmpty()){
        for( OrderDishData orderDishData : orderDishes) {

            Dish dish = dishRepository.findById(orderDishData.getDishId()).orElseThrow(
                    () -> new EntityNotFoundException("Dish with ID " + orderDishData.getDishId() + " not found")
            );

            OrderDish orderDish = OrderDish.builder()
                    .order(order)
                    .dish(dish)
                    .quantity(orderDishData.getQuantity())
                    .state(OrderDishState.IN_PROGRESS)
                    .build();

            orderDishRepository.save(orderDish);
        }
    }

}

    @Override
    public OrderUpdateDTO updateOrder(UUID id, OrderUpdateDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Tables table = tableRepository.findById(orderDTO.getTable())
                .orElseThrow(() -> new EntityNotFoundException("Table not found"));

        if (orderDTO.getWaiterId() != null) {
            User waiter = userRepository.findById(orderDTO.getWaiterId())
                    .orElseThrow(() -> new EntityNotFoundException("Waiter not found"));
            order.setWaiterId(waiter);
        }

        order.setTable(table);
        order.setState(orderDTO.getState());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setPaymentMethod(orderDTO.getPaymentMethod());

        Order updatedOrder = orderRepository.save(order);

        if (orderDTO.getOrderDishes() != null) {
            List<OrderDish> existingDishes = orderDishRepository.findByOrderId(order.getId());
            if (!existingDishes.isEmpty()) {
                orderDishRepository.deleteAll(existingDishes);
            }

            for (OrderDishData dishData : orderDTO.getOrderDishes()) {
                Dish dish = dishRepository.findById(dishData.getDishId())
                        .orElseThrow(() -> new EntityNotFoundException("Dish not found: " + dishData.getDishId()));

                OrderDish orderDish = OrderDish.builder()
                        .order(order)
                        .dish(dish)
                        .quantity(dishData.getQuantity())
                        .state(OrderDishState.IN_PROGRESS)
                        .build();

                orderDishRepository.save(orderDish);
            }
        }

        return OrderUpdateDTO.builder()
                .table(updatedOrder.getTable().getId())
                .waiterId(updatedOrder.getWaiterId().getUserId())
                .state(updatedOrder.getState())
                .totalAmount(updatedOrder.getTotalAmount())
                .paymentMethod(updatedOrder.getPaymentMethod())
                .orderDishes(orderDTO.getOrderDishes())
                .build();
    }



    @Override
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Order not found")
        );
        orderRepository.delete(order);
    }

    @Override
    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order not found.")
        );
    }

    @Override
    public Page<OrderSummaryDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(order -> OrderSummaryDTO.builder()
                        .id(order.getId())
                        .tableNumber(order.getTable().getNumber())
                        .waiterName(order.getWaiterId().getFirstName() + " " + order.getWaiterId().getLastName())
                        .state(order.getState().name())
                        .totalAmount(order.getTotalAmount())
                        .paymentMethod(order.getPaymentMethod().name())
                        .build());
    }


    public Page<Order> findAllByState(String state, Pageable pageable) {
        return orderRepository.findAllByState(OrderState.valueOf(state.toUpperCase()), pageable);

    }
    @Override
    public Page<Order> findAllByWaiterId(Integer waiterId, Pageable pageable) {

        User waiter = userRepository.findById(waiterId).orElseThrow(
                () -> new EntityNotFoundException("Waiter not found.")
        );

        return orderRepository.findAllByWaiterId(waiter, pageable);
    }

    @Override
    public Page<Order> findAllByPaymentMethod(String paymentMethod, Pageable pageable) {
        return orderRepository.findAllByPaymentMethod(OrderPaymentMethod.valueOf(paymentMethod.toUpperCase()), pageable);
    }
}
