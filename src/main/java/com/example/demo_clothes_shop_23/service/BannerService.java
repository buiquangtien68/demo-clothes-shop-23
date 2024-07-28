package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Banner;
import com.example.demo_clothes_shop_23.repository.BannerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    public List<Banner> getBannerByStatus(Boolean status) {
        return bannerRepository.findByStatus(status);
    }
}
