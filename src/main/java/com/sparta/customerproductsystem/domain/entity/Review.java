package com.sparta.customerproductsystem.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "product_name")
    private String productName;

    private double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public static Review create(Users user, Product product, double rating) {

        Review r = new Review();
        r.user = user;
        r.product = product;
        r.username = user.getName();
        r.productName = product.getName();
        r.rating = rating;

        product.addReview(r);
        user.addReview(r);
        return r;
    }
}
