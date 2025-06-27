package org.torres.backendkitchen.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderCreateDTO;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderSummaryDTO;
import org.torres.backendkitchen.Domain.DTO.Orders.OrderUpdateDTO;
import org.torres.backendkitchen.Domain.Entity.Order;

import java.util.UUID;

public interface iOrderService {
    void createOrder(OrderCreateDTO orderDTO);

    OrderUpdateDTO updateOrder(UUID id, OrderUpdateDTO orderDTO);

    void deleteOrder(UUID id) throws Exception;

    Order findById(UUID id);

    Page<OrderSummaryDTO> findAll(Pageable pageable);

    Page<Order> findAllByState(String state, Pageable pageable);

    Page<Order> findAllByWaiterId(Integer waiterId, Pageable pageable);

    Page<Order> findAllByPaymentMethod(String paymentMethod, Pageable pageable);

}
