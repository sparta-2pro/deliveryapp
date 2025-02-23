package com.twopro.deliveryapp.order.service;

import com.twopro.deliveryapp.order.dto.FindOrderResponseDto;
import com.twopro.deliveryapp.order.dto.OrderRequestDto;
import com.twopro.deliveryapp.order.dto.OrderStatusRequestDto;
import com.twopro.deliveryapp.order.dto.PaymentRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    void createOrder(OrderRequestDto requestDto, Long userId);

    void paymentRequest(PaymentRequestDto requestDto, Long userId);

    FindOrderResponseDto findOrder(UUID orderId, Long userId);

    Page<FindOrderResponseDto> findOrders(Long userId, int page, Integer size, String sortBy, Boolean isAsc);

    void deleteOrder(UUID orderId, Long userId);

    void updateStatus(OrderStatusRequestDto requestDto, Long userId);

}
