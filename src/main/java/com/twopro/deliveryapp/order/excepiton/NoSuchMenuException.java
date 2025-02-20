package com.twopro.deliveryapp.order.excepiton;

import com.twopro.deliveryapp.order.dto.CreateOrderMenuDto;
import lombok.Getter;

import java.util.List;

@Getter
public class NoSuchMenuException extends RuntimeException{
    private Long userId;
    private List<CreateOrderMenuDto> menus;

    public NoSuchMenuException(String message, Long userId, List<CreateOrderMenuDto> menus) {
        super(message);
        this.userId = userId;
        this.menus = menus;
    }


}
