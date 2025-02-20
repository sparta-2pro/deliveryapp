package com.twopro.deliveryapp.common.exceptionHandler.order;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.order.excepiton.CustomOrderCancelException;
import com.twopro.deliveryapp.order.excepiton.OrderStoreMinPriceException;
import com.twopro.deliveryapp.order.excepiton.OrderUpdateStatusException;
import com.twopro.deliveryapp.order.excepiton.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestAdviceController {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @ExceptionHandler(OrderStoreMinPriceException.class)
    public ResponseEntity handleOrderStoreMinPriceException(OrderStoreMinPriceException e) {
        log.info("errorMessage : {}, userId: {}, totalPrice : {}", e.getMessage(), e.getUserId(), e.getTotalPrice());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "ORDER_ERROR_1"));
    }

    @ExceptionHandler(OrderUpdateStatusException.class)
    public ResponseEntity handleOrderUpdateStatusException(OrderUpdateStatusException e) {
        log.info("errorMessage : {}, userId : {}, orderId : {}, orderStatus : {} ", e.getMessage(), e.getUserId(), e.getOrderId(), e.getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "ORDER_ERROR_2"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleOUserNotFoundException(UserNotFoundException e) {
        log.info("errorMessage : {}, userId: {}", e.getMessage(), e.getUserId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "ORDER_ERROR_3"));
    }

    @ExceptionHandler(CustomOrderCancelException.class)
    public ResponseEntity handleOCustomOrderCancelException(CustomOrderCancelException e) {
        log.info("errorMessage : {}, userId: {}, orderId: {}, orderStatus : {}", e.getMessage(), e.getUserId(), e.getOrderId(), e.getOrderStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "ORDER_ERROR_3"));
    }

}
