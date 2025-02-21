package com.twopro.deliveryapp.menu.service;

import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.MenuResponseDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.exception.MenuNotFoundException;
import com.twopro.deliveryapp.menu.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public MenuResponseDto addMenu(AddMenuRequestDto addMenuRequestDto) {
        Menu menu = menuRepository.addMenu(Menu.of(addMenuRequestDto));

        return MenuResponseDto.from(menu);
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
        List<Menu> menus = findAllMenuByStoreIdForServer(storeId);

        return getMenuResponseDtoList(menus);
    }

    @Override
    public List<MenuResponseDto> findAllMenuByName(String name) {
        List<Menu> menus = findAllMenuByNameForServer(name);

        return getMenuResponseDtoList(menus);
    }

    // TODO 빈 리스트를 반환하는 게 좋아보임
    private List<Menu> findAllMenuByStoreIdForServer(UUID storeId) {
        return menuRepository.findAllMenuByStoreId(storeId)
                .orElseThrow(() -> new MenuNotFoundException("현재 가게에 등록된 메뉴가 없어요!"));
    }

    // TODO 빈 리스트를 반환하는 게 좋아보임
    private List<Menu> findAllMenuByNameForServer(String name) {
        return menuRepository.findAllMenuByName(name)
                .orElseThrow(() -> new MenuNotFoundException("해당 이름을 가진 메뉴가 없어요!"));
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
