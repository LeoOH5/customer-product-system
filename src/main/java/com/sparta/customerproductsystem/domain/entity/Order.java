package com.sparta.customerproductsystem.domain.entity;

import com.sparta.customerproductsystem.domain.role.OrderRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private int amount;

    private OrderRole status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Order(Users user, Product product, int quantity, int amount) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
    }

    public void setStatus(OrderRole status) {
        this.status = status;
    }

    public void update(int quantity, int amount, Product product) {
        this.quantity = quantity;
        this.amount = amount;
        this.product = product;
    }
}
