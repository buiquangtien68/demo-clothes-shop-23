package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Image;
import com.example.demo_clothes_shop_23.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> getAllByColor_IdAndProduct_Id(Integer colorId, Integer productId) {
        return imageRepository.findAllByColor_IdAndProduct_Id(colorId, productId);
    }

}
