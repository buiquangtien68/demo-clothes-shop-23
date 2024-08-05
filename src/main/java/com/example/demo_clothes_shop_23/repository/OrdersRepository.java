package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findByCodeOrder(String codeOrder);
}
