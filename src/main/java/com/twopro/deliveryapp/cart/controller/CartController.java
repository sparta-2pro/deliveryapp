package com.twopro.deliveryapp.cart.controller;

import com.twopro.deliveryapp.cart.dto.CartMenuDto;
import com.twopro.deliveryapp.cart.dto.CartResponseDto;
import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.cart.service.CartService;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MenuService menuService;

    // 장바구니 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<SingleResponse<CartResponseDto>> getCartByUserId(@PathVariable("user_id") Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new SingleResponse<>(new CartResponseDto(cart)));
    }

    // 장바구니에 메뉴추가
    @PostMapping("/{user_id}/menu")
    public ResponseEntity<?> addMenuToCart(@RequestBody CartMenuDto cartMenuDto,
                                           @PathVariable("user_id") Long userId) {
        // Cart를 가져오거나 새로 생성
        Cart cart = cartService.getOrCreatecart(userId);
        System.out.println("cartId: " + cart.getCartId());  // 디버깅용 로그 추가

        // 장바구니에 메뉴 추가
        cartService.addMenuToCart(cart.getCartId(), cartMenuDto);

        return ResponseEntity.ok("메뉴가 장바구니에 담겼습니다.");
    }

    // 장바구니에 메뉴 수량 변경
    @PutMapping("/{user_id}/menu/{menu_id}")
    public ResponseEntity<?> updateMenuQuantity(@PathVariable("user_id") Long userId,
                                                @PathVariable("menu_id") UUID menuId,
                                                @RequestParam int quantity){
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니를 찾을 수 없습니다.");
        }

        MenuResponseDto menuResponseDto = menuService.findMenuById(menuId);
        if (menuResponseDto  == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("메뉴를 찾을 수 없습니다.");
        }

        cartService.updateMenuQuantity(cart.getCartId(), menuId, quantity);

        return ResponseEntity.ok("메뉴 수량이 변경되었습니다.");

    }

    // 장바구니에서 메뉴 삭제
    @DeleteMapping("/{cart_id}/menu/{menu_id}")
    public ResponseEntity<SingleResponse<String>> removeMenuFromCart(
            @PathVariable("cart_id") UUID cartId,
            @PathVariable("menu_id") UUID menuId) {
        cartService.removeMenuFromCart(cartId, menuId);
        return ResponseEntity.ok(new SingleResponse<>("메뉴가 장바구니에서 삭제되었습니다."));
    }

    // 장바구니 비우기
    @DeleteMapping("/{cart_id}/menu")
    public ResponseEntity<SingleResponse<String>> clearCart(@PathVariable("cart_id") UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new SingleResponse<>("장바구니가 비워졌습니다!"));
    }

    // 장바구니 총 가격
    @GetMapping("/{cart_id}/total-price")
    public ResponseEntity<SingleResponse<Integer>> getTotalPrice(@PathVariable("cart_id") UUID cartId) {
        int totalPrice = cartService.calculateTotalPrice(cartId);
        return ResponseEntity.ok(new SingleResponse<>(totalPrice));
    }
}
