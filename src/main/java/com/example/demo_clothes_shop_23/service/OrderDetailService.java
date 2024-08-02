package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.*;
import com.example.demo_clothes_shop_23.repository.*;
import com.example.demo_clothes_shop_23.request.CreateOrderDetailRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderDetailService {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final OrdersDetailRepository ordersDetailRepository;

    public OrdersDetail createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest) {
        Orders order = ordersRepository.findById(createOrderDetailRequest.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepository.findById(createOrderDetailRequest.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        Color color = colorRepository.findById(createOrderDetailRequest.getColorId())
            .orElseThrow(() -> new RuntimeException("Color not found"));
        Size size = sizeRepository.findById(createOrderDetailRequest.getSizeId())
            .orElseThrow(() -> new RuntimeException("Size not found"));
        OrdersDetail ordersDetail = OrdersDetail.builder()
            .orders(order)
            .product(product)
            .color(color)
            .size(size)
            .quantity(createOrderDetailRequest.getQuantity())
            .price(product.getNewPrice()*createOrderDetailRequest.getQuantity())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        ordersDetailRepository.save(ordersDetail);
        return ordersDetail;
    }
}
