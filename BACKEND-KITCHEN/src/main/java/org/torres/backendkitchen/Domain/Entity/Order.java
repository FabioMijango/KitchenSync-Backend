package org.torres.backendkitchen.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.torres.backendkitchen.Domain.Enum.OrderPaymentMethod;
import org.torres.backendkitchen.Domain.Enum.OrderState;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tableId", nullable = false)
    private Tables table;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User waiterId;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private OrderState state;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private OrderPaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDish> orderDishes;

}
