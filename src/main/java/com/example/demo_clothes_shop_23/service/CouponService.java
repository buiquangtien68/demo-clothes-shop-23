package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Coupon;
import com.example.demo_clothes_shop_23.exception.ResourceNotFoundException;
import com.example.demo_clothes_shop_23.repository.CouponRepository;
import com.example.demo_clothes_shop_23.request.UseCouponRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CouponService {
    private CouponRepository couponRepository;

    public Coupon getCoupon(Integer userId,String couponCode) {
        // Tìm coupon theo mã và trạng thái hoạt động
        Coupon coupon = couponRepository.findByCodeAndActive(couponCode, true);

        // Kiểm tra xem coupon có tồn tại không
        if (coupon == null) {
            throw new ResourceNotFoundException("Coupon not found");
        }

        // Kiểm tra xem coupon có còn số lượng không
        if (coupon.getQuantity() == 0) {
            throw new RuntimeException("Coupon has no quantity");
        }

        // Nếu danh sách chưa được khởi tạo, khởi tạo nó
        if (coupon.getListUserIdUsed() == null) {
            coupon.setListUserIdUsed(new ArrayList<>());
        }

        // Kiểm tra xem người dùng đã sử dụng coupon chưa
        if (coupon.getListUserIdUsed() != null && coupon.getListUserIdUsed().contains(userId)) {
            throw new RuntimeException("User already used coupon");
        }

        return coupon;
    }

}
