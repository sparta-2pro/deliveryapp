package com.twopro.deliveryapp.order.service;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import com.twopro.deliveryapp.order.dto.CreateOrderMenuDto;
import com.twopro.deliveryapp.order.dto.FindOrderResponseDto;
import com.twopro.deliveryapp.order.dto.OrderCreateRequestDto;
import com.twopro.deliveryapp.order.dto.OrderMenuResponseDto;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.excepiton.OrderAccessDeniedException;
import com.twopro.deliveryapp.order.repository.OrderRepository;
import com.twopro.deliveryapp.orderItem.Entity.OrderItem;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService implements OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createOrder(OrderCreateRequestDto requestDto, Long userId){

//        User user = userRepository.findById(userId).orElseThrow();
//        List<MenuEntity> findMenus = menuRepository.findByMenuIdIn(requestDto.getMenus().stream().map(CreateOrderMenuDto::getMenuId).toList());
//
//        Address address = Address.of(requestDto.getAddress());
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        for (CreateOrderMenuDto menuDto : requestDto.getMenus()) {
//            MenuEntity menu = findMenus.stream()
//                    .filter(m -> m.getMenuId().equals(menuDto.getMenuId()))
//                    .findFirst()
//                    .orElseThrow(); // 메뉴 ID가 존재하지 않으면 예외 발생
//            orderItems.add(OrderItem.createOrderItem(menu, menuDto.getMenuPrice(), menuDto.getQuantity()));
//        }
//
//        Order order = Order.createOrder(user, orderItems, requestDto.getOrderType(), address);
//        Order save = orderRepository.save(order);
    }

//    public OrderMenuResponseDto findOrder(UUID orderId, Long userId) {
//        Order order = orderRepository.findById(orderId).orElseThrow();
//        validateOrderOwnership(userId, order);
//
//        return FindOrderResponseDto()
//    }

    @Transactional
    public void deleteOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        validateOrderOwnership(userId, order);

        order.delete();
    }

    /**
     * 주문자와 접근하려는 사람이 일치하는 지 확인
     * @param userId 사용자 id
     * @param order DB에서 조회해 온 Order
     */
    private static void validateOrderOwnership(Long userId, Order order) {
        if (!Objects.equals(order.getUser().getUser_id(), userId)) {
            throw new OrderAccessDeniedException("주문자와 주문 조회자가 일치하지 않습니다.");
        }
    }
}
