package org.torres.backendkitchen.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.torres.backendkitchen.Domain.Entity.Order;
import org.torres.backendkitchen.Domain.Entity.OrderDish;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface iOrderDishRepository extends JpaRepository<OrderDish, UUID> {

    Optional<OrderDish> findById(UUID id);

    List<OrderDish> findAllByOrder(Order order);

    List<OrderDish> findByOrderId(UUID orderId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM OrderDish od WHERE od.dish.id = :dishId")
    void deleteByDishId(@Param("dishId") UUID dishId);

}
