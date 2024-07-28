package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Review;
import com.example.demo_clothes_shop_23.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> findByProductId(Integer productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> findByProduct_IdOrderByCreatedAtDesc(int productId) {
        return reviewRepository.findByProduct_IdOrderByCreatedAtDesc(productId);
    }
}
