package com.twopro.deliveryapp.menu.service;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.store.entity.Store;

import java.util.List;

public interface MenuService {

    SingleResponse<MenuResponseDto> addMenu(AddMenuRequestDto addMenuRequestDto, Store store);

    SingleResponse<MenuResponseDto> findMenuById(String menuId);

    SingleResponse<List<MenuResponseDto>> findAllMenuByStoreId(String storeId);

    SingleResponse<List<MenuResponseDto>> findAllMenuByName(String name);

    SingleResponse<MenuResponseDto> updateMenu(String menuId, UpdateMenuRequestDto updateMenuRequestDto);

    void deleteMenu(String menuId);
}
