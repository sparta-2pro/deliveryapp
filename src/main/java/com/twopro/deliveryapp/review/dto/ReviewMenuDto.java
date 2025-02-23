package com.twopro.deliveryapp.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewMenuDto {
    private UUID menuId;
    private String menuName;;
    private int price;
    private int quantity;
}
