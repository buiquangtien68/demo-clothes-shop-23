package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Coupon;
import com.example.demo_clothes_shop_23.entities.Orders;
import com.example.demo_clothes_shop_23.entities.Quantity;
import com.example.demo_clothes_shop_23.entities.User;
import com.example.demo_clothes_shop_23.exception.ResourceNotFoundException;
import com.example.demo_clothes_shop_23.model.enums.DeliveryType;
import com.example.demo_clothes_shop_23.model.enums.OrdersStatus;
import com.example.demo_clothes_shop_23.model.enums.PaymentType;
import com.example.demo_clothes_shop_23.repository.CouponRepository;
import com.example.demo_clothes_shop_23.repository.OrdersRepository;
import com.example.demo_clothes_shop_23.request.CreateOrderRequest;
import com.example.demo_clothes_shop_23.security.CustomUserDetails;
import com.example.demo_clothes_shop_23.vnPay.config.PaymentConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderService {
    private final CouponRepository couponRepository;
    private final OrdersRepository ordersRepository;

    public Orders getByCodeOrder(String codeOrder) {
        return ordersRepository.findByCodeOrder(codeOrder);
    }

    public Orders createOrder(CreateOrderRequest createOrderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = (User) userDetails.getUser();
        if (!Objects.equals(createOrderRequest.getCouponCode(), "")){
            Coupon coupon = couponRepository.findByCodeAndActive(createOrderRequest.getCouponCode(), true);
            if (coupon != null) {
                if (coupon.getQuantity() > 0) {  // Kiểm tra số lượng mã giảm giá
                    coupon.setQuantity(coupon.getQuantity() - 1);
                    List<Integer> usedUserIds = coupon.getListUserIdUsed();
                    if (usedUserIds == null) {
                        usedUserIds = new ArrayList<>();
                    }
                    usedUserIds.add(createOrderRequest.getUserId());
                    coupon.setListUserIdUsed(usedUserIds);
                    couponRepository.save(coupon);
                } else {
                    // Xử lý khi mã giảm giá hết số lượng
                    throw new RuntimeException("Mã giảm giá đã hết số lượng");
                }
            } else {
                // Xử lý khi mã giảm giá không tồn tại hoặc không hợp lệ
                throw new RuntimeException("Mã giảm giá không hợp lệ");
            }
        }

        Orders orders = Orders.builder()
            .user(user)
            .email(createOrderRequest.getEmail())
            .receiverName(createOrderRequest.getReceiverName())
            .phone(createOrderRequest.getPhone())
            .province(createOrderRequest.getProvince())
            .district(createOrderRequest.getDistrict())
            .ward(createOrderRequest.getWard())
            .addressDetail(createOrderRequest.getAddressDetail())
            .totalPrice(createOrderRequest.getTotalPrice())
            .discountAmount(createOrderRequest.getDiscountAmount())
            .finalTotal(createOrderRequest.getFinalTotal())
            .status(OrdersStatus.CHO_XAC_NHAN)
            .payment(PaymentType.valueOf(createOrderRequest.getPayment()))
            .delivery(DeliveryType.valueOf(createOrderRequest.getDelivery()))
            .notes(createOrderRequest.getNotes())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        ordersRepository.save(orders);
        return orders;
    }

    public Orders updateCodeOrder(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setCodeOrder(PaymentConfig.getRandomNumber(8));
        ordersRepository.save(order);
        return order;
    }
}
