package org.torres.backendkitchen.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.torres.backendkitchen.Domain.Enum.OrderDishState;

import java.util.UUID;


@Entity
@Data
@Table(name="orderDishes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dishId", nullable = false)
    private Dish dish;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private OrderDishState state; // Type ENUM of DishState


}
