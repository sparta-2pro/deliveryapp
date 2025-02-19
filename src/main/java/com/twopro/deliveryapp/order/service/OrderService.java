package com.twopro.deliveryapp.order.service;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import com.twopro.deliveryapp.order.dto.*;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.excepiton.OrderAccessDeniedException;
import com.twopro.deliveryapp.order.excepiton.OrderStoreMinPriceException;
import com.twopro.deliveryapp.order.repository.OrderRepository;
import com.twopro.deliveryapp.orderItem.Entity.OrderItem;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createOrder(OrderCreateRequestDto requestDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow();
        Store findStore = storeRepository.findById(null).orElseThrow();
        if (findStore.getMinimumOrderPrice() > requestDto.getMenus().stream().mapToInt(m -> m.getMenuPrice() * m.getQuantity()).sum()) {
            throw new OrderStoreMinPriceException("최소 주문 금액을 맞춰주세요");
        }

        List<Menu> findMenus = menuRepository.findByMenuIdIn(requestDto.getMenus().stream().map(CreateOrderMenuDto::getMenuId).toList());
        Address address = Address.of(requestDto.getAddressDto());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderMenuDto menuDto : requestDto.getMenus()) {
            Menu menu = findMenus.stream()
                    .filter(m -> m.getMenuId().equals(menuDto.getMenuId()))
                    .findFirst()
                    .orElseThrow(); // 메뉴 ID가 존재하지 않으면 예외 발생
            orderItems.add(OrderItem.createOrderItem(menu, menuDto.getMenuPrice(), menuDto.getQuantity()));
        }

        Order order = Order.createOrder(user, orderItems, requestDto.getOrderType(), address, requestDto.getMessage());
        orderRepository.save(order);

//        return new OrderCreateResponseDto(order.getId(), requestDto.getAddressDto(), requestDto.getOrderType(), order.getTotalPrice(), requestDto.getMenus());
    }

    public FindOrderResponseDto findOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        validateOrderOwnership(userId, order);

        Order findOrder = orderRepository.findByOrderItemsAndMenu(order).orElseThrow();

        List<OrderMenuResponseDto> orderMenuResponseDto = new ArrayList<>();
        findOrder.getOrderItems().stream().forEach(oi -> orderMenuResponseDto.add(new OrderMenuResponseDto(oi.getMenu().getName(), oi.getOrderPrice(), oi.getCount())));

        return new FindOrderResponseDto(findOrder.getId(), AddressDto.of(findOrder.getAddress()), findOrder.getMessage(), findOrder.getOrderType(), findOrder.getTotalPrice(), orderMenuResponseDto);
    }

    @Transactional
    public void deleteOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        validateOrderOwnership(userId, order);

        order.getOrderItems().forEach(BaseEntity::delete);
        order.delete();
    }

    /**
     * 주문자와 접근하려는 사람이 일치하는 지 확인
     *
     * @param userId 사용자 id
     * @param order  DB에서 조회해 온 Order
     */
    private static void validateOrderOwnership(Long userId, Order order) {
        if (!Objects.equals(order.getUser().getUserId(), userId)) {
            throw new OrderAccessDeniedException("주문자와 주문 조회자가 일치하지 않습니다.");
        }
    }
}
