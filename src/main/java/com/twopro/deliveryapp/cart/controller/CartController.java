package com.twopro.deliveryapp.cart.controller;

import com.twopro.deliveryapp.cart.dto.CartMenuDto;
import com.twopro.deliveryapp.cart.dto.CartResponseDto;
import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.cart.service.CartService;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<SingleResponse<CartResponseDto>> getCartByUserId(@PathVariable("user_id") Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new SingleResponse<>(new CartResponseDto(cart)));
    }

    @PostMapping("/{user_id}/menus")
    public ResponseEntity<?> addMenuToCart(@RequestBody CartMenuDto cartMenuDto,
                                           @PathVariable("user_id") Long userId) {
        // Cart를 가져오거나 새로 생성
        Cart cart = cartService.getOrCreatecart(userId);
        System.out.println("cartId: " + cart.getCartId());  // 디버깅용 로그 추가

        // 장바구니에 메뉴 추가
        cartService.addMenuToCart(cart.getCartId(), cartMenuDto);

        return ResponseEntity.ok("메뉴가 장바구니에 담겼습니다.");
    }

    // 장바구니에서 메뉴 삭제
    @DeleteMapping("/{cart_id}/menus/{cart_menu_id}")
    public ResponseEntity<SingleResponse<String>> removeMenuFromCart(
            @PathVariable("cart_id") UUID cartId,
            @PathVariable("cart_menu_id") Long cartMenuId) {
        cartService.removeMenuFromCart(cartId, cartMenuId);
        return ResponseEntity.ok(new SingleResponse<>("메뉴가 장바구니에서 삭제되었습니다."));
    }
}
