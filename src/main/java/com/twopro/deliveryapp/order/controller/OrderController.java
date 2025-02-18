package com.twopro.deliveryapp.order.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.order.dto.FindOrderResponseDto;
import com.twopro.deliveryapp.order.dto.OrderCreateRequestDto;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.excepiton.OrderAccessDeniedException;
import com.twopro.deliveryapp.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    // 주문 요청
    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderCreateRequestDto requestDto, @RequestParam Long userId) {
        orderService.createOrder(requestDto, userId);
        return null;
    }

    // 사용자 주문 취소
    @PatchMapping("/{orderId}")
    public ResponseEntity cancelOrder(@PathVariable UUID orderId, @RequestParam Long userId) {
        orderService.deleteOrder(orderId, userId);
        return ResponseEntity.ok(null);
    }

    // 주문 단일 조회
    @GetMapping("/{orderId}")
    public ResponseEntity getOrder(@PathVariable UUID orderId, @RequestParam Long userId) {

        FindOrderResponseDto result = orderService.findOrder(orderId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(SingleResponse.success(result));
    }

    // 사용자 주문 전체 조회
    @GetMapping("/userorders")
    public ResponseEntity getUserOrders(@RequestParam Long userId,
                                              @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size,
                                              @RequestParam(required = false) String sortBy,
                                              @RequestParam(required = false) Boolean isAsc) {
        return ResponseEntity.ok(null);
    }

    // 결제 요청
    @PostMapping("/{orderId}/payment")
    public ResponseEntity requestPayment(@PathVariable Long orderId,
                                               @RequestBody Map<String, Object> paymentRequest,
                                               @RequestParam Long userId) {
        return ResponseEntity.ok(null);
    }

    // 가게주의 주문 상태 변경
    @PutMapping("/{orderId}/status")
    public ResponseEntity updateOrderStatus(@PathVariable Long orderId,
                                                  @RequestBody Map<String, String> statusUpdate,
                                                  @RequestParam Long userId) {
        return ResponseEntity.ok(null);
    }

    // 해당 가게 주문 내역 조회

}


