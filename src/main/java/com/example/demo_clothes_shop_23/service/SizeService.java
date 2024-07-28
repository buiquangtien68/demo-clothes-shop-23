package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Size;
import com.example.demo_clothes_shop_23.model.enums.SizeType;
import com.example.demo_clothes_shop_23.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SizeService {
    private final SizeRepository sizeRepository;

    public List<Size> findAllSizes() {
        return sizeRepository.findAll();
    }
    public Set<Size> findSizeByTypeOrderByOrdersAsc(SizeType type){
        return sizeRepository.findSizeByTypeOrderByOrdersAsc(type);
    }
}
