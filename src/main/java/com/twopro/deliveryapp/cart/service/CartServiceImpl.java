package com.twopro.deliveryapp.cart.service;

import com.twopro.deliveryapp.cart.dto.CartMenuDto;
import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.cart.entity.CartMenu;
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

    // user_id로 장바구니 조회
    @Override
    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "장바구니가 존재하지 않습니다."));

        if (cart.getCartMenus().isEmpty()) {
            throw new CustomException(HttpStatus.NO_CONTENT, "장바구니가 비어 있습니다.");
        }

        return cart;
    }

    // 장바구니에 메뉴 추가
    @Override
    @Transactional
    public Cart addMenuToCart(UUID cartId, CartMenuDto cartMenuDto) {
        // 장바구니 조회
        Cart cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        // 메뉴 조회
        Menu menu = menuRepository.findMenuById(cartMenuDto.getCartMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        // CartMenu 엔티티 생성
        CartMenu cartMenu = cartMenuDto.toEntity(menu, cart);

        // 장바구니에 메뉴 추가
        cart.getCartMenus().add(cartMenu);

        return cart;
    }

    @Override
    public Cart getOrCreatecart(Long userId) {
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

    @Override
    @Transactional
    public void removeMenuFromCart(UUID cartId, Long cartMenuId) {
        Cart cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));

        CartMenu cartMenu = cart.getCartMenus().stream()
                .filter(menu -> menu.getCartMenuId().equals(cartMenuId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("장바구니 메뉴를 찾을 수 없습니다."));

        cart.getCartMenus().remove(cartMenu);  // 메뉴 삭제
    }
}
