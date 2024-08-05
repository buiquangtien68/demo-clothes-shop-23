package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Quantity;
import com.example.demo_clothes_shop_23.repository.QuantityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuantityService {
    private final QuantityRepository quantityRepository;

    public Quantity getByProductIdAndColorIdAndSizeId(Integer productId, Integer colorId, Integer sizeId) {
        return quantityRepository.findByProductIdAndColorIdAndSizeId(productId, colorId, sizeId);
    }
}