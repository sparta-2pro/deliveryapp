package com.twopro.deliveryapp.menu.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.service.MenuService;
import com.twopro.deliveryapp.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public SingleResponse<MenuResponseDto> addMenu(AddMenuRequestDto addMenuRequestDto, Store store) {

        return null;
    }
}
