package com.twopro.deliveryapp.common.enumType;

public enum OrderStatus {
    OWNER_CANCELLED, // 가게주 취소
    DELIVERING, // 배달출발
    CUSTOMER_REQUESTED, //가장 기본 - 손님이 결제요청한 상황
    OWNER_ACCEPTED, // 가게주가 확인한 상황
    ORDER_REQUESTED,// 주문이 들어왔고 가게에게 알린 상황
    CUSTOMER_CANCELLED, // 손님 취소
    PAYMENT_ERROR,
    DELIVERY_SUCCESS// 배달완료

}
