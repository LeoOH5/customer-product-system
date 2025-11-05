package com.sparta.customerproductsystem.domain.entity;

import com.sparta.customerproductsystem.domain.role.ProductRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
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
        this.totalSales += sales;
    }
}