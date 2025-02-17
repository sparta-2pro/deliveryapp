package com.twopro.deliveryapp.order.service;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.order.dto.OrderCreateRequestDto;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.excepiton.CustomException;
import com.twopro.deliveryapp.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements OrderServiceImpl {

    private final OrderRepository orderRepository;

    public void createOrder(OrderCreateRequestDto requestDto, Long userId){
        Order order = new Order();
        order.setAddress(Address.of(requestDto.getAddress()));
        order.setOrderType(requestDto.getOrderType());
        orderRepository.save(order);
        order.setOrderType(requestDto.getOrderType());

    }

    public void delete(){
        Order order = new Order();
        order.delete();
    }














    public static void main(String[] args) {
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        List<Integer> list = integers.stream().filter(a -> a > 3).toList();
        list.forEach(System.out::println);
    }
}
