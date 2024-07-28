package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Color;
import com.example.demo_clothes_shop_23.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

}
