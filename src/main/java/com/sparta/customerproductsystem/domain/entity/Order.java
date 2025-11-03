package com.sparta.customerproductsystem.domain.entity;

import com.sparta.customerproductsystem.domain.role.OrderRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    private int amount;

    private OrderRole status;

    public Order(Users user, Product product, int quantity, int amount) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
    }
}
