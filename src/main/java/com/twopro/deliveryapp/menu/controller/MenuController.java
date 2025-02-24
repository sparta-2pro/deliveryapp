package com.twopro.deliveryapp.menu.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.menu.service.MenuService;
import com.twopro.deliveryapp.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public SingleResponse<MenuResponseDto> addMenu(@RequestBody AddMenuRequestDto addMenuRequestDto) {
        MenuResponseDto addMenuResponse = menuService.addMenu(addMenuRequestDto);
        return SingleResponse.success(addMenuResponse);
    }

    @GetMapping("/{menuId}")
    public SingleResponse<MenuResponseDto> findMenuById(@PathVariable UUID menuId) {
        return SingleResponse.success(menuService.findMenuById(menuId));
    }

    @GetMapping("/store")
    public SingleResponse<List<MenuResponseDto>> findAllMenuByStoreId(@RequestParam UUID storeId) {
        List<MenuResponseDto> findMenuResponseDtoList = menuService.findAllMenuByStoreId(storeId);

        return SingleResponse.success(findMenuResponseDtoList);
    }

    @GetMapping("/name")
    public SingleResponse<List<MenuResponseDto>> findAllMenuByName(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String name,
            @RequestParam(required = false) UUID lastMenuId,
            @RequestParam int size
    ) {
        // TODO get chain 좋지 않음. 주요 비즈니스 규칙이라 메서드로 밸 수 있도록 담당자에게 전달해야 함
        String receiveLocation = userDetails.getUser().getAddress().getEupMyeonDong();
        log.info("receive location: {}", receiveLocation);
        List<MenuResponseDto> findMenuResponseDtoList =
                menuService.findAllMenuByName(receiveLocation, name, lastMenuId, size);

        return SingleResponse.success(findMenuResponseDtoList);
    }

    @PatchMapping("/{menuId}")
    public SingleResponse<MenuResponseDto> updateMenu(
            @PathVariable UUID menuId,
            @RequestBody UpdateMenuRequestDto updateMenuRequestDto
    ) {
        MenuResponseDto updateMenuResponse = menuService.updateMenu(menuId, updateMenuRequestDto);

        return SingleResponse.success(updateMenuResponse);
    }

    @PatchMapping("/delete/{menuId}")
    public void deleteMenu(@PathVariable UUID menuId) {
        menuService.deleteMenu(menuId);
    }
}
