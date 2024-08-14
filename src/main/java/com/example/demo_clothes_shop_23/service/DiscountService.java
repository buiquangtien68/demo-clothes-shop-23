package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Discount;
import com.example.demo_clothes_shop_23.entities.Product;
import com.example.demo_clothes_shop_23.model.enums.DiscountType;
import com.example.demo_clothes_shop_23.repository.DiscountRepository;
import com.example.demo_clothes_shop_23.repository.ProductRepository;
import com.example.demo_clothes_shop_23.request.UpsertDiscountRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public List<Discount> getAll() {
        return discountRepository.findAll();
    }

    public List<Discount> getDiscountByActive(Boolean active) {
        return discountRepository.findByActive(active);
    }

    public Discount getDiscountById(Integer id) {
        return discountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Discount not found"));
    }

    public void createDiscount(UpsertDiscountRequest upsertDiscountRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(upsertDiscountRequest.getStartDate(), formatter);
            endDate = LocalDate.parse(upsertDiscountRequest.getEndDate(), formatter);

            LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0)); // 00:00:00
            LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

            Discount discount = Discount.builder()
                .name(upsertDiscountRequest.getName())
                .description(upsertDiscountRequest.getDescription())
                .type(DiscountType.valueOf(upsertDiscountRequest.getType()))
                .amount(upsertDiscountRequest.getAmount())
                .active(upsertDiscountRequest.getActive())
                .startDate(startDateTime)
                .endDate(endDateTime)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            discountRepository.save(discount);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ngày tháng không hợp lệ");
        }
    }

    public void updateDiscount(UpsertDiscountRequest upsertDiscountRequest, Integer discountId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate;
        LocalDate endDate;

        try {
            Discount discount = discountRepository.findById(discountId).orElseThrow(
                () -> new RuntimeException("Discount not found"));

            startDate = LocalDate.parse(upsertDiscountRequest.getStartDate(), formatter);
            endDate = LocalDate.parse(upsertDiscountRequest.getEndDate(), formatter);

            LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0)); // 00:00:00
            LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

            discount.setName(upsertDiscountRequest.getName());
            discount.setDescription(upsertDiscountRequest.getDescription());
            discount.setType(DiscountType.valueOf(upsertDiscountRequest.getType()));
            discount.setAmount(upsertDiscountRequest.getAmount());
            discount.setActive(upsertDiscountRequest.getActive());
            discount.setStartDate(startDateTime);
            discount.setEndDate(endDateTime);
            discount.setUpdatedAt(LocalDateTime.now());

            discountRepository.save(discount);
            if (discount.getActive()){
                List<Product> oldProducts = productRepository.findByDiscount_Id(discount.getId());
                oldProducts.forEach(product -> {
                    product.setDiscount(null);
                    product.setNewPrice(product.getPrice());
                    productRepository.save(product);
                });

                List<Product> newProducts = productRepository.findAllById(upsertDiscountRequest.getProductIds());
                newProducts.forEach(product -> {
                    product.setDiscount(discount);
                    DiscountType discountType = DiscountType.valueOf(upsertDiscountRequest.getType());
                    if (discountType==DiscountType.PERCENT){
                        // Lấy giá sản phẩm và tính toán giá mới sau khi giảm giá
                        double price = product.getPrice();
                        double discountAmount = product.getDiscount().getAmount();
                        double newPrice = price * (1 - discountAmount / 100);

                        product.setNewPrice((long) newPrice);
                    }else if (discountType==DiscountType.SAME_PRICE){
                        // Lấy giá sản phẩm và tính toán giá mới sau khi giảm giá
                        double discountAmount = product.getDiscount().getAmount();

                        product.setNewPrice((long) discountAmount);
                    }else {
                        // Lấy giá sản phẩm và tính toán giá mới sau khi giảm giá
                        double price = product.getPrice();
                        double discountAmount = product.getDiscount().getAmount();
                        long newPrice = (long) (price - discountAmount);

                        product.setNewPrice(newPrice);
                    }
                    productRepository.save(product);
                });
            }else {
                List<Product> oldProducts = productRepository.findByDiscount_Id(discount.getId());
                oldProducts.forEach(product -> {
                    product.setDiscount(null);
                    product.setNewPrice(product.getPrice());
                    productRepository.save(product);
                });
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ngày tháng không hợp lệ");
        }
    }
}
