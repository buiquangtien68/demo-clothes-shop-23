package com.example.demo_clothes_shop_23.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UpsertCommentRequest {
    @NotEmpty(message = "Không được để nội dung trống")
    String content;
    Integer blogId;
}
