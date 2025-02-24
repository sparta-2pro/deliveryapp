package com.twopro.deliveryapp.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.order.dto.FindOrderResponseDto;
import com.twopro.deliveryapp.order.dto.OrderRequestDto;
import com.twopro.deliveryapp.order.dto.OrderStatusRequestDto;
import com.twopro.deliveryapp.order.dto.PaymentRequestDto;
import com.twopro.deliveryapp.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    // 주문하러 가기 클릭 상황
    @PostMapping
    public ResponseEntity createOrder(@RequestBody @Valid OrderRequestDto requestDto, @RequestParam Long userId) {
        log.debug("REQUSET :: {}", requestDto);
        orderService.createOrder(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(SingleResponse.success("success"));
    }

    // 결제 요청
    @PostMapping("/payment")
    public ResponseEntity requestPayment(@RequestBody PaymentRequestDto requestDto, @RequestParam Long userId) {
        log.debug("REQUSET :: {}", requestDto);
        orderService.paymentRequest(requestDto, userId);
        return ResponseEntity.ok(SingleResponse.success("success"));
    }

    // 사용자 주문 취소
    @PatchMapping("/{orderId}")
    public ResponseEntity cancelOrder(@PathVariable UUID orderId, @RequestParam Long userId) {
        log.debug("orderId :: {}, userId :: {}", orderId, userId);
        orderService.deleteOrder(orderId, userId);
        return ResponseEntity.ok(SingleResponse.success("success"));
    }

    // 주문 단일 조회
    @GetMapping("/{orderId}")
    public ResponseEntity getOrder(@PathVariable UUID orderId, @RequestParam Long userId) {
        log.debug("orderId :: {}, userId :: {}", orderId, userId);
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
        log.debug("userId :: {}, page :: {}, size :: {}, sortBy :: {}, isAsc :: {}", userId, size, page, sortBy, isAsc);
        Page<FindOrderResponseDto> orders = orderService.findOrders(userId, page - 1, size, sortBy, isAsc);
        return ResponseEntity.ok(MultiResponse.success(orders));
    }

    // 가게주의 주문 확인, 가게주의 배달 출발, 가게주위 배달 취소
    @PutMapping("/{orderId}/status")
    public ResponseEntity updateOrderStatus(@RequestParam Long userId, @RequestBody OrderStatusRequestDto requestDto) {
        log.debug("userId :: {}, requestDto :: {}", userId, requestDto);
        orderService.updateStatus(requestDto, userId);
        return ResponseEntity.ok(SingleResponse.success("success"));
    }
}


