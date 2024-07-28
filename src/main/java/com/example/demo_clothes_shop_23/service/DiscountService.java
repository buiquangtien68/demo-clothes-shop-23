package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Discount;
import com.example.demo_clothes_shop_23.repository.DiscountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public List<Discount> getDiscountByActive(Boolean active) {
        return discountRepository.findByActive(active);
    }

    public Discount getDiscountById(Integer id) {
        return discountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Discount not found"));
    }
}
