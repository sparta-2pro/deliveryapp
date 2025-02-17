package com.twopro.deliveryapp.menu.service;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;

import java.util.List;

public interface MenuService {

    SingleResponse<MenuResponseDto> addMenu(AddMenuRequestDto addMenuRequestDto);

    SingleResponse<List<MenuResponseDto>> findAllMenuByStoreId(String storeId);

    SingleResponse<List<MenuResponseDto>> findAllMenuByName(String menuName);

    SingleResponse<MenuResponseDto> updateMenu(String menuId, UpdateMenuRequestDto updateMenuRequestDto);

    void deleteMenu(String menuId);
}
