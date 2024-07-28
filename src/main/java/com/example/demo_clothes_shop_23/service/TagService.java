package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Tag;
import com.example.demo_clothes_shop_23.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag getTagById(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }
}
