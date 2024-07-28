package com.example.demo_clothes_shop_23.entities;

import com.example.demo_clothes_shop_23.model.enums.DeliveryType;
import com.example.demo_clothes_shop_23.model.enums.OrdersStatus;
import com.example.demo_clothes_shop_23.model.enums.PaymentType;
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
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(nullable = false)
    String receiverName;

    String phone;

    String province;
    String district;
    String ward;
    @Column(columnDefinition = "TEXT")
    String addressDetail;

    Double totalPrice;

    OrdersStatus status;
    PaymentType payment;
    DeliveryType delivery;

    @Column(columnDefinition = "TEXT")
    String notes;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
