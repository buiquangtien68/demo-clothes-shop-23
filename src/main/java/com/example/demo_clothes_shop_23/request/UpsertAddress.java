package com.example.demo_clothes_shop_23.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertAddress {
    @Column(nullable = false)
    String receiverName;

    String phone;

    String province;
    String district;
    String ward;
    @Column(columnDefinition = "TEXT")
    String addressDetail;
    Boolean chosen;
}
