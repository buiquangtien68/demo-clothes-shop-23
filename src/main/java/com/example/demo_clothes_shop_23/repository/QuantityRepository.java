package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<Quantity, Integer> {
}