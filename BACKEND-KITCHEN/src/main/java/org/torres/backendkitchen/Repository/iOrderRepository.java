package org.torres.backendkitchen.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.torres.backendkitchen.Domain.Entity.Order;
import org.torres.backendkitchen.Domain.Entity.Tables;
import org.torres.backendkitchen.Domain.Entity.User;
import org.torres.backendkitchen.Domain.Enum.OrderPaymentMethod;
import org.torres.backendkitchen.Domain.Enum.OrderState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface iOrderRepository extends JpaRepository<Order, UUID> {

    public Optional<Order> findById(UUID uuid);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByState(OrderState state, Pageable pageable);

    Page<Order> findAllByWaiterId(User waiterId, Pageable pageable);

    Page<Order> findAllByPaymentMethod(OrderPaymentMethod paymentMethod, Pageable pageable);

    List<Order> findByTable(Tables table);

}
