package com.example.demo_clothes_shop_23.entities;

import com.example.demo_clothes_shop_23.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    String avatar;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;


    @Enumerated(EnumType.STRING)
    UserRole role;

    @ManyToMany
    @JoinTable(
            name = "user_coupon",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    List<Coupon> coupons;


    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
