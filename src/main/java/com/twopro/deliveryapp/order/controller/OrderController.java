package com.twopro.deliveryapp.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.order.dto.*;
import com.twopro.deliveryapp.order.service.OrderService;
import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'OWNER')")
public class OrderController {
    private final OrderService orderService;

    // 주문하러 가기 클릭 상황
    @PostMapping
    public ResponseEntity createOrder(@RequestBody @Valid OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.debug("REQUSET :: {}", requestDto);
        orderService.createOrder(requestDto, userDetails.getUser().getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(SingleResponse.success("success"));
    }

    // 결제 요청
    @PostMapping("/payment")
    public ResponseEntity requestPayment(@RequestBody PaymentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.debug("REQUSET :: {}", requestDto);
        PaymentResponseDto paymentResponseDto = orderService.paymentRequest(requestDto, userDetails.getUser().getUserId());
        return ResponseEntity.ok(SingleResponse.success(paymentResponseDto));
    }

    // 사용자 주문 취소
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/{orderId}")
    public ResponseEntity cancelOrder(@PathVariable UUID orderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.debug("orderId :: {}, userId :: {}", orderId, userDetails.getUser().getUserId());
        orderService.deleteOrder(orderId, userDetails.getUser().getUserId());
        return ResponseEntity.ok(SingleResponse.success("success"));
    }

    // 주문 단일 조회
    @GetMapping("/{orderId}")
    public ResponseEntity getOrder(@PathVariable UUID orderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.debug("orderId :: {}, userId :: {}", orderId, userDetails.getUser().getUserId());
        FindOrderResponseDto result = orderService.findOrder(orderId, userDetails.getUser().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(SingleResponse.success(result));
    }

    // 사용자 주문 전체 조회
    @GetMapping("/userorders")
    public ResponseEntity getUserOrders(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(required = false, defaultValue = "10") Integer size,
                                              @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                              @RequestParam(required = false, defaultValue = "false") Boolean isAsc) {
        log.debug("userId :: {}, page :: {}, size :: {}, sortBy :: {}, isAsc :: {}", userDetails.getUser().getUserId(), size, page, sortBy, isAsc);
        Page<FindOrderResponseDto> orders = orderService.findOrders(userDetails.getUser().getUserId(), page - 1, size, sortBy, isAsc);
        return ResponseEntity.ok(MultiResponse.success(orders));
    }

    // 가게주의 주문 확인, 가게주의 배달 출발, 가게주의 배달 취소
    @PutMapping("/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity updateOrderStatus(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody OrderStatusRequestDto requestDto) {
        log.debug("userId :: {}, requestDto :: {}", userDetails.getUser().getUserId(), requestDto);
        orderService.updateStatus(requestDto, userDetails.getUser().getUserId());
        return ResponseEntity.ok(SingleResponse.success("success"));
    }
}


