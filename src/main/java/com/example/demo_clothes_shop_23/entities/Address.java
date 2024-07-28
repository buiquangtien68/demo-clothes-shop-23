package com.example.demo_clothes_shop_23.entities;

import com.example.demo_clothes_shop_23.model.enums.UserRole;
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
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String receiverName;

    String phone;

    String province;
    String district;
    String ward;
    @Column(columnDefinition = "TEXT")
    String addressDetail;
    Boolean chosen;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
