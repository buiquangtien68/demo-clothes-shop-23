package com.example.demo_clothes_shop_23.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ordersDetails")
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    Integer quantity;

    Double price;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}