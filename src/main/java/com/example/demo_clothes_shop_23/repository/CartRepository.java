package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}