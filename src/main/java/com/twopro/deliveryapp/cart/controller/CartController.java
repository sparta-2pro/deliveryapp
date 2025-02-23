package com.twopro.deliveryapp.cart.controller;

import com.twopro.deliveryapp.cart.dto.CartMenuDto;
import com.twopro.deliveryapp.cart.dto.CartResponseDto;
import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.cart.service.CartService;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.service.MenuService;
import com.twopro.deliveryapp.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 조회
    @GetMapping("/my-cart")
    public ResponseEntity<SingleResponse<CartResponseDto>> getCartByUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Cart cart = cartService.getCartByUserId(userDetails.getUser().getUserId());

        // 장바구니가 비었을 때
        if (cart == null || cart.getCartMenus().isEmpty()) {
            return ResponseEntity.ok(new SingleResponse<>(new CartResponseDto(cart)));  // 비어있는 장바구니 메시지를 포함한 DTO 반환
        }

        CartResponseDto cartResponseDto = new CartResponseDto(cart);
        // 로그로 확인
        System.out.println("CartResponseDto: " + cartResponseDto.getMessage());
        return ResponseEntity.ok(new SingleResponse<>(cartResponseDto));
    }

    // 장바구니에 메뉴추가
    @PostMapping("/menus")
    public ResponseEntity<?> addMenuToCart(@RequestBody CartMenuDto cartMenuDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Cart를 가져오거나 새로 생성
        Cart cart = cartService.getOrCreateCart(userDetails.getUser().getUserId());
        System.out.println("cartId: " + cart.getCartId());  // 디버깅용 로그 추가

        // 장바구니에 메뉴 추가
        cartService.addMenuToCart(cart.getCartId(), cartMenuDto);

        return ResponseEntity.ok("메뉴가 장바구니에 담겼습니다.");
    }

    // 장바구니에 메뉴 수량 변경
    @PatchMapping("/menu/{menu_id}")
    public ResponseEntity<?> updateMenuQuantity(@PathVariable("menu_id") UUID menuId,
                                                @RequestParam(name="quantity") int quantity,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){

        Cart cart = cartService.getCartByUserId(userDetails.getUser().getUserId());
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("장바구니를 찾을 수 없습니다.");
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
    @DeleteMapping("/menus")
    public ResponseEntity<SingleResponse<String>> clearCart(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.clearCartByUser(userDetails.getUser().getUserId());
        return ResponseEntity.ok(new SingleResponse<>("장바구니가 비워졌습니다!"));
    }

    // 장바구니 총 가격 -> ex) 메뉴1 * 2개 + 메뉴2 * 1개 합
    @GetMapping("/total-price")
    public ResponseEntity<SingleResponse<String>> getTotalPrice(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // userDetails에서 userId 가져와서 calculateTotalPrice로 전달
        int totalPrice = cartService.calculateTotalPrice(userDetails.getUser().getUserId());

        // 가격 메시지 반환
        String message = totalPrice > 0 ? totalPrice + " 원입니다. 결제하시겠습니까?" : "장바구니가 비어있습니다.";
        return ResponseEntity.ok(new SingleResponse<>(message));
    }
}
