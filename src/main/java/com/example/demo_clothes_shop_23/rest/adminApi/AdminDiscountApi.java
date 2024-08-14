package com.example.demo_clothes_shop_23.rest.adminApi;

import com.example.demo_clothes_shop_23.request.UpsertDiscountRequest;
import com.example.demo_clothes_shop_23.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/discounts")
@AllArgsConstructor
public class AdminDiscountApi {
    private final DiscountService discountService;

    @PutMapping("/create")
    public ResponseEntity<?> createDiscount(@RequestBody UpsertDiscountRequest upsertDiscountRequest) {
        discountService.createDiscount(upsertDiscountRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{discountId}/update")
    public ResponseEntity<?> updateDiscount(@RequestBody UpsertDiscountRequest upsertDiscountRequest, @PathVariable Integer discountId) {
        discountService.updateDiscount(upsertDiscountRequest, discountId);
        return ResponseEntity.ok().build();
    }

}
