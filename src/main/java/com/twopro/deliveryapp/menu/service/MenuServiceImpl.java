package com.twopro.deliveryapp.menu.service;

import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.exception.MenuNotFoundException;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final StoreService storeService;

    @Override
    @Transactional
    public MenuResponseDto addMenu(AddMenuRequestDto addMenuRequestDto) {
        Menu menu = menuRepository.addMenu(Menu.of(addMenuRequestDto));
        menu.setStore(getStore(addMenuRequestDto.storeId()));

        return MenuResponseDto.from(menu);
    }

    // TODO store 담당자가 서비스 레이어에서 optional 처리해줘야 함
    private Store getStore(UUID storeId) {
        return storeService.findByID(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 없음"));
    }

    @Override
    public MenuResponseDto findMenuById(UUID menuId) {
        return MenuResponseDto.from(findMenuByIdForServer(menuId));
    }

    private Menu findMenuByIdForServer(UUID menuId) {
        return menuRepository.findMenuById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("해당 Id 를 가진 메뉴를 찾을 수 없어요!"));
    }

    @Override
    public List<Menu> findByMenuIdIn(List<UUID> menuIds) {
        return menuRepository.findByMenuIdIn(menuIds);
    }

    @Override
    public List<MenuResponseDto> findAllMenuByStoreId(UUID storeId) {
        List<Menu> menus = menuRepository.findAllMenuByStoreId(storeId);

        return getMenuResponseDtoList(menus);
    }

    @Override
    public List<MenuResponseDto> findAllMenuByName(String receiveLocation, String name, UUID lastMenuId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "menuId"));

        List<Menu> menus = lastMenuId == null
                ? menuRepository.findAllMenuByName(receiveLocation, name, pageable)
                : menuRepository.findAllMenuByName(receiveLocation, name, lastMenuId, pageable);

        return getMenuResponseDtoList(menus);
    }

    private static List<MenuResponseDto> getMenuResponseDtoList(List<Menu> menus) {
        return menus.stream()
                .map(MenuResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public MenuResponseDto updateMenu(UUID menuId, UpdateMenuRequestDto updateMenuRequestDto) {
        Menu menu = findMenuByIdForServer(menuId);
        Menu updatedMenu = Menu.update(menu, updateMenuRequestDto);

        return MenuResponseDto.from(updatedMenu);
    }

    @Override
    @Transactional
    public void deleteMenu(UUID menuId) {
        Menu menu = findMenuByIdForServer(menuId);
        menu.delete();
    }
}
