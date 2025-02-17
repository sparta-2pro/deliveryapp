package com.twopro.deliveryapp.menu.service;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.menu.entity.MenuEntity;
import com.twopro.deliveryapp.menu.exception.MenuNotFoundException;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import com.twopro.deliveryapp.store.entity.Store;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public SingleResponse<MenuResponseDto> addMenu(AddMenuRequestDto addMenuRequestDto, Store store) {
        MenuEntity menuEntity = menuRepository.addMenu(MenuEntity.of(addMenuRequestDto, store));

        return SingleResponse.success(MenuResponseDto.from(menuEntity));
    }

    @Override
    public SingleResponse<MenuResponseDto> findMenuById(String menuId) {
        MenuResponseDto menuResponseDto = MenuResponseDto.from(findMenuByIdForServer(menuId));

        return SingleResponse.success(menuResponseDto);
    }

    private MenuEntity findMenuByIdForServer(String menuId) {
        return menuRepository.findMenuById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("해당 Id 를 가진 메뉴를 찾을 수 없어요!"));
    }

    @Override
    public SingleResponse<List<MenuResponseDto>> findAllMenuByStoreId(String storeId) {
        List<MenuEntity> menus = findAllMenuByStoreIdForServer(storeId);

        return SingleResponse.success(getMenuResponseDtoList(menus));
    }

    @Override
    public SingleResponse<List<MenuResponseDto>> findAllMenuByName(String name) {
        List<MenuEntity> menus = findAllMenuByNameForServer(name);

        return SingleResponse.success(getMenuResponseDtoList(menus));
    }

    // TODO 빈 리스트를 반환하는 게 좋아보임
    private List<MenuEntity> findAllMenuByStoreIdForServer(String storeId) {
        return menuRepository.findAllMenuByStoreId(storeId)
                .orElseThrow(() -> new MenuNotFoundException("현재 가게에 등록된 메뉴가 없어요!"));
    }

    // TODO 빈 리스트를 반환하는 게 좋아보임
    private List<MenuEntity> findAllMenuByNameForServer(String name) {
        return menuRepository.findAllMenuByName(name)
                .orElseThrow(() -> new MenuNotFoundException("해당 이름을 가진 메뉴가 없어요!"));
    }

    private static List<MenuResponseDto> getMenuResponseDtoList(List<MenuEntity> menus) {
        return menus.stream()
                .map(MenuResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public SingleResponse<MenuResponseDto> updateMenu(String menuId, UpdateMenuRequestDto updateMenuRequestDto) {
        MenuEntity menuEntity = findMenuByIdForServer(menuId);
        MenuEntity updatedMenu = MenuEntity.update(menuEntity, updateMenuRequestDto);

        return SingleResponse.success(MenuResponseDto.from(updatedMenu));
    }

    @Override
    @Transactional
    public void deleteMenu(String menuId) {
        MenuEntity menuEntity = findMenuByIdForServer(menuId);
        menuEntity.delete();
    }
}
