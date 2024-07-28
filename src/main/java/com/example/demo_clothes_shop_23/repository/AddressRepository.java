package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
