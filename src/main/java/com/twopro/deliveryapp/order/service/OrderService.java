package com.twopro.deliveryapp.order.service;

import com.twopro.deliveryapp.order.dto.FindOrderResponseDto;
import com.twopro.deliveryapp.order.dto.OrderRequestDto;
import com.twopro.deliveryapp.order.dto.OrderStatusRequestDto;
import com.twopro.deliveryapp.order.dto.PaymentRequestDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    public void createOrder(OrderRequestDto requestDto, Long userId);

    public void paymentRequest(PaymentRequestDto requestDto, Long userId);

    public FindOrderResponseDto findOrder(UUID orderId, Long userId);

    public List<FindOrderResponseDto> findOrders(Long userId, int page, Integer size, String sortBy, Boolean isAsc);

    public void deleteOrder(UUID orderId, Long userId);

    void updateStatus(OrderStatusRequestDto requestDto, Long userId);
}
