package com.twopro.deliveryapp.menu.service;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.store.entity.Store;

import java.util.List;
import java.util.UUID;

public interface MenuService {

    SingleResponse<MenuResponseDto> addMenu(AddMenuRequestDto addMenuRequestDto, Store store);

    SingleResponse<MenuResponseDto> findMenuById(UUID menuId);

    SingleResponse<List<MenuResponseDto>> findAllMenuByStoreId(UUID storeId);

    SingleResponse<List<MenuResponseDto>> findAllMenuByName(String name);

    SingleResponse<MenuResponseDto> updateMenu(UUID menuId, UpdateMenuRequestDto updateMenuRequestDto);

    void deleteMenu(UUID menuId);
}
