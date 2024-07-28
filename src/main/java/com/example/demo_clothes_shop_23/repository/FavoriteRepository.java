package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
}
