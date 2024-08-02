package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Address;
import com.example.demo_clothes_shop_23.entities.User;
import com.example.demo_clothes_shop_23.repository.AddressRepository;
import com.example.demo_clothes_shop_23.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> getByUser_Id() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            User user = (User) customUserDetails.getUser();
            return addressRepository.findByUser_Id(user.getId());
        }else return new ArrayList<>();
    }

    public Address getByUser_IdAndChosen() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            User user = (User) customUserDetails.getUser();
            return addressRepository.findByUser_IdAndChosen(user.getId(), true);
        }else return null;
    }

}
