package com.twopro.deliveryapp.cart.service;

import com.twopro.deliveryapp.cart.dto.CartMenuDto;
import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.cart.entity.CartMenu;
import com.twopro.deliveryapp.cart.repository.CartMenuRepository;
import com.twopro.deliveryapp.cart.repository.CartRepository;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.exception.CustomException;
import com.twopro.deliveryapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final CartMenuRepository cartMenuRepository;

    // 내 장바구니 조회
    @Override
    public Cart getCartByUserId(Long userId) {
        // 장바구니가 없으면 빈 Cart 객체를 반환
        return cartRepository.findByUser_UserId(userId)
                .orElse(new Cart());
    }

    // 장바구니에 메뉴 추가
    @Override
    @Transactional
    public void addMenuToCart(UUID cartId, CartMenuDto cartMenuDto) {
        // 장바구니 조회
        Cart cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        // 메뉴 조회
        Menu menu = menuRepository.findMenuById(cartMenuDto.getMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        // 장바구니에서 해당 메뉴가 이미 있는지 확인
        CartMenu existingCartMenu = cart.findExistingCartMenuByMenuId(menu.getMenuId());

        if (existingCartMenu != null) {
            // 이미 장바구니에 있으면 수량만 증가시킴
            existingCartMenu.setQuantity(existingCartMenu.getQuantity() + cartMenuDto.getQuantity());
            existingCartMenu.setTotalPrice(existingCartMenu.getQuantity() * menu.getPrice()); // 총 가격 갱신
        } else {
            // 없으면 새로 CartMenu 추가
            CartMenu cartMenu = cartMenuDto.toEntity(menu, cart);
            // 장바구니에 메뉴 추가
            cart.getCartMenus().add(cartMenu);
            cartMenuRepository.save(cartMenu);
        }
        cartRepository.save(cart);

    }

    @Override
    public Cart getOrCreateCart(Long userId) {
        // 현재 사용자에 해당하는 장바구니를 찾거나 새로 생성
        Cart cart = cartRepository.findByUser_UserId(userId).orElse(null);
        if (cart == null) {
            cart = createCart(userId); // 새 장바구니 생성
        }
        return cart;
    }

    private Cart createCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        Cart cart = new Cart();
        cart.setUser(user);  // user와 연관 설정
        cart.setCartMenus(new ArrayList<>());
        return cartRepository.save(cart);  // 장바구니 저장
    }

    // 메뉴 수량 변경
    @Override
    public void updateMenuQuantity(UUID cartId, UUID menuId, int quantity) {
        System.out.println("Updating menu quantity..."); // 디버깅용 로그
        CartMenu cartMenu = cartMenuRepository.findByCart_CartIdAndMenu_MenuId(cartId, menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 메뉴가 장바구니에 없습니다."));
        System.out.println("Found cartMenu: " + cartMenu); // cartMenu가 제대로 조회됐는지 확인

        cartMenu.setQuantity(quantity);
        cartMenuRepository.save(cartMenu);
        System.out.println("Updated quantity: " + cartMenu.getQuantity()); // 업데이트된 quantity 확인

    }

    // 장바구니에 메뉴 제거
    @Override
    @Transactional
    public void removeMenuFromCart(UUID cartId, UUID menuId) {
        Cart cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        CartMenu cartMenu = cartMenuRepository.findByCart_CartIdAndMenu_MenuId(cartId, menuId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "장바구니에서 해당 메뉴를 찾을 수 없습니다."));

        cartMenuRepository.delete(cartMenu);
    }

    // 장바구니 비우기
    @Override
    public void clearCartByUser(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."));
        cart.getCartMenus().clear();
        cartRepository.save(cart);
    }

    // 총 가격
    @Override
    public int calculateTotalPrice(Long userId) {
        // userId로 장바구니 찾기
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        // 장바구니 메뉴들의 총 가격 계산
        int totalPrice = cart.getCartMenus().stream()
                .mapToInt(CartMenu::getTotalPrice).sum();
        return totalPrice;
    }
}
