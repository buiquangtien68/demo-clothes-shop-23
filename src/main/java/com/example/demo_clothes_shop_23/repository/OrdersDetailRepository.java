package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.OrdersDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
    @Query("SELECT SUM(od.price) FROM OrdersDetail od WHERE od.orders.id = :orderId")
    Double findTotalPriceByOrderId(@Param("orderId") Integer orderId);

    List<OrdersDetail> findByOrdersId(Integer orderId);
}
