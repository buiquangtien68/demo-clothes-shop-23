package com.example.demo_clothes_shop_23.rest;

import com.example.demo_clothes_shop_23.entities.Orders;
import com.example.demo_clothes_shop_23.request.CreateOrderRequest;
import com.example.demo_clothes_shop_23.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderApi {
    private final OrderService orderService;

    @PutMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        Orders order = orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}