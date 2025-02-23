package com.twopro.deliveryapp.order.service;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.common.enumType.OrderStatus;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.service.MenuService;
import com.twopro.deliveryapp.order.dto.*;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.excepiton.*;
import com.twopro.deliveryapp.order.repository.OrderRepository;
import com.twopro.deliveryapp.orderItem.Entity.OrderItem;
import com.twopro.deliveryapp.payment.entity.Payment;
import com.twopro.deliveryapp.payment.service.PaymentServiceImpl;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.service.StoreService;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import com.twopro.deliveryapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentServiceImpl paymentService;
    private final StoreService storeService;
    private final MenuService menuService;
    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * 사용자가 음식을 선택하고 결제페이지로 넘어가는 로직
     * 최소주문금액 확인
     * <p>
     * {가게 id,
     * [상품id, 가격, 갯수],
     * [상품id, 가격, 갯수]
     * }
     */

    @Override
    @Transactional
    public void createOrder(OrderRequestDto requestDto, Long userId) {
        //가게 id로 가게 찾아오기
        //리팩토링 필요, 가게 id로 menu 정보 한번에 찾아올 수 있을 듯
        Store findStore = storeService.findByID(requestDto.getStoreId()).orElseThrow(() -> new StoreNotFoundException("가게ID와 일치하는 가게가 없습니다."));

        //requestDto에 들어온 menuId로 실제 db의 menu의 가격 가져와서 최소주문금액 구하는 로직
        List<Menu> findMenus = menuService.findByMenuIdIn(requestDto.getMenus().stream().map(CreateOrderMenuDto::getMenuId).toList());
        int totalPrice = 0;
        for (CreateOrderMenuDto menuDto : requestDto.getMenus()) {
            Menu menu = findMenus.stream()
                    .filter(m -> m.getMenuId().equals(menuDto.getMenuId()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchMenuException("메뉴를 찾을 수 없습니다.", userId, requestDto.getMenus())); // 메뉴 ID가 존재하지 않으면 예외 발생
            totalPrice += menu.getPrice() * menuDto.getQuantity();
        }

        //최소주문금액 확인 로직
        if (findStore.getMinimumOrderPrice() > totalPrice) {
            throw new OrderStoreMinPriceException("최소 주문 금액을 맞춰주세요", totalPrice, userId);
        }
    }

    /**
     * 사용자가 주문하기 버튼 클릭한 상황 로직
     * 가게 영업중인지 확인
     */

    @Override
    @Transactional
    public void paymentRequest(PaymentRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저Id와 일치하는 유저 정보가 없습니다.", userId));
        Store findStore = storeService.findByID(requestDto.getStoreId()).orElseThrow();

        // 영업중인지 확인 로직
        if (!findStore.getStatus().equals(StoreStatus.OPEN)) { // 추후 N은 변경 예정
            throw new StoreNoOpenException("영업중인 가게가 아닙니다.");
        }

        //======================================================================================================================
        //배달가능 지역 로직 넣어야 됌
        //======================================================================================================================

        List<Menu> findMenus = menuService.findByMenuIdIn(requestDto.getMenus().stream().map(CreateOrderMenuDto::getMenuId).toList());
        Address address = Address.of(requestDto.getAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;
        for (CreateOrderMenuDto menuDto : requestDto.getMenus()) {
            // 메뉴 찾기
            Menu menu = findMenus.stream()
                    .filter(m -> m.getMenuId().equals(menuDto.getMenuId()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchMenuException("메뉴를 찾을 수 없습니다.", userId, requestDto.getMenus()));
            // OrderItem 추가
            orderItems.add(OrderItem.createOrderItem(menu, menu.getPrice(), menuDto.getQuantity()));
            // totalPrice 계산
            totalPrice += menu.getPrice() * menuDto.getQuantity();
        }
        // 결제 처리
        Payment payment = paymentService.createPayment(totalPrice, requestDto.getPaymentProvider(), userId);
        // 주문 생성
        Order order = Order.createOrder(user, orderItems, requestDto.getOrderType(), address, requestDto.getMessage(), payment, findStore);
        orderRepository.save(order);

    }


    /**
     * 오더 단건 조회
     *
     * @param orderId 오더ID
     * @param userId  로그인 유저ID
     */

    @Override
    public FindOrderResponseDto findOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findByOrderItemsAndMenu(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다.", userId, orderId));

        validateOrderOwnership(userId, order);

        List<OrderMenuResponseDto> orderMenuResponseDto = order.getOrderItems().stream()
                .map(oi -> new OrderMenuResponseDto(oi.getMenu().getName(), oi.getOrderPrice(), oi.getCount()))
                .toList();

        return new FindOrderResponseDto(
                order.getId(),
                AddressDto.of(order.getAddress()),
                order.getMessage(),
                order.getOrderType(),
                order.getTotalPrice(),
                orderMenuResponseDto
        );
    }

    /**
     * 사용자 주문내역들 전체 조회
     */

    @Override
    public Page<FindOrderResponseDto> findOrders(Long userId, int page, Integer size, String sortBy, Boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저Id와 일치하는 유저 정보가 없습니다.", userId));

        Page<Order> findOrders = orderRepository.findAllByUser(user, pageable);

        return findOrders.map(order -> new FindOrderResponseDto(
                order.getId(),
                AddressDto.of(order.getAddress()),
                order.getMessage(),
                order.getOrderType(),
                order.getTotalPrice(),
                order.getOrderItems().stream()
                        .map(oi -> new OrderMenuResponseDto(oi.getMenu().getName(), oi.getOrderPrice(), oi.getCount()))
                        .toList()
        ));
    }


    /**
     * 주문삭제
     *
     * @param orderId 오더ID
     * @param userId  로그인 유저ID
     *                추가 로직 구성 필요 - 손님이 취소 요청이 들어와도 OrderStatus값 보고 가게주의 주문확인이 안됐을 경우에만 취소 가능하게 변경.
     */

    @Override
    @Transactional
    public void deleteOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다.", userId, orderId));
        validateOrderOwnership(userId, order);

        if (order.getOrderStatus().equals(OrderStatus.CUSTOMER_REQUESTED)) { // 아직 가게주가 주문확인을 안했을 경우만 취소
            order.updateStatus(OrderStatus.CUSTOMER_CANCELLED);
            order.getOrderItems().forEach(BaseEntity::delete);
            order.delete();
        } else {
            throw new CustomOrderCancelException("이미 가게주가 음식을 만드는 중입니다.", userId, orderId, order.getOrderStatus());
        }
    }

    /**
     * @param requestDto 오더Id와 상태값 DTO
     * @param userId     로그인 유저ID
     *                   가게주의 주문 확인, 가게주의 주문 거절, 가게주의 배달 출발
     */
    @Override
    @Transactional
    public void updateStatus(OrderStatusRequestDto requestDto, Long userId) {
        Order findOrder = orderRepository.findById(requestDto.getOrderId()).orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다.", userId, requestDto.getOrderId()));
        if (requestDto.getOrderStatus().equals(OrderStatus.OWNER_ACCEPTED)) { //가게주 주문 확인
            findOrder.updateStatus(requestDto.getOrderStatus());
        } else if (requestDto.getOrderStatus().equals(OrderStatus.OWNER_CANCELLED)) {//가게주 주문 취소
            findOrder.updateStatus(requestDto.getOrderStatus());
            findOrder.delete();
        } else if (requestDto.getOrderStatus().equals(OrderStatus.DELIVERING)) {
            findOrder.updateStatus(requestDto.getOrderStatus());
        } else {
            throw new OrderUpdateStatusException("잘못된 오더 상태 변경값입니다.", userId, requestDto.getOrderId(), requestDto.getOrderStatus());
        }
    }

    public Order findById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("주문을 찾을 수 없습니다"));
    }

    /**
     * 주문자와 접근하려는 사람이 일치하는 지 확인
     *
     * @param userId 사용자 id
     * @param order  DB에서 조회해 온 Order
     */
    private void validateOrderOwnership(Long userId, Order order) {
        if (!Objects.equals(order.getUser().getUserId(), userId)) {
            throw new OrderAccessDeniedException("주문자와 주문 조회자가 일치하지 않습니다.");
        }
    }

    /**
     * Pageable 객체 생성 메서드
     */
    private Pageable createPageable(int page, Integer size, String sortBy, Boolean isAsc) {
        Sort.Direction direction = (isAsc != null && isAsc) ? Sort.Direction.ASC : Sort.Direction.DESC;

        String defaultSortBy = "createdAt";
        Sort sort = (sortBy == null || sortBy.trim().isEmpty())
                ? Sort.by(direction, defaultSortBy)
                : Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }
}
