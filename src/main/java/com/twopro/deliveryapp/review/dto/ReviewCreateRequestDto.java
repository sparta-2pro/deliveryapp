package com.twopro.deliveryapp.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class ReviewCreateRequestDto {
    @NotNull(message = "orderId는 필수입니다.")
    private UUID orderId;

    @Length(max = 255)
    private String content;

    @NotNull(message = "rating 값은 필수 입력 항목입니다.")
    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5 이하여야 합니다.")
    private Integer rating;
}
