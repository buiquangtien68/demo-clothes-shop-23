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
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String code;
    Integer amount;

    LocalDateTime startDate;
    LocalDateTime endDate;
    Boolean active;
    Integer quantity;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
