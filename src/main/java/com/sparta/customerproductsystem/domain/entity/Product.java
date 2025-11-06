package com.sparta.customerproductsystem.domain.entity;

import com.sparta.customerproductsystem.domain.role.ProductRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor
public class Product extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private String category;

    private String imageUrl;

    private Long totalSales;

    // 양방향
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private final List<Review> reviews = new ArrayList<>(); // NPE 방지

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    void addReview(Review review) {
        if (review == null) return;
        this.reviews.add(review);
    }

    @Builder
    private Product(String name, String description, int price,
                    int stockQuantity, String category, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public ProductRole inferStatus() {
        if (stockQuantity <= 0) {
            return ProductRole.OUT_OF_STOCK;
        }
        return ProductRole.AVAILABLE;
    }

    public void setTotalSales(int stockQuantity, int sales) {

        this.stockQuantity = stockQuantity - sales;

        if(totalSales == null) {
            this.totalSales = 0L;
        }

        this.totalSales += sales;
    }
}