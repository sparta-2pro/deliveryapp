package com.twopro.deliveryapp.menu.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public SingleResponse<List<MenuResponseDto>> findAllMenuByName(@RequestParam String name) {
        List<MenuResponseDto> findMenuResponseDtoList = menuService.findAllMenuByName(name);

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
