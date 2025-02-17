package com.twopro.deliveryapp.menu.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "P_MENU")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String menuId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Store store;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private MenuStatus status;

    // TODO s3? test url?
    private String imageUrl;

    private String description;

    @Column(nullable = false)
    private int price;

    public static MenuEntity of(AddMenuRequestDto addMenuRequestDto, Store store) {
        return MenuEntity.builder()
                .store(store)
                .name(addMenuRequestDto.name())
                .status(addMenuRequestDto.status())
                .imageUrl(addMenuRequestDto.imageUrl())
                .description(addMenuRequestDto.description())
                .price(addMenuRequestDto.price())
                .build();
    }

    public static MenuEntity update(MenuEntity menuEntity, UpdateMenuRequestDto updateMenuRequestDto) {
        menuEntity.name = updateMenuRequestDto.name();
        menuEntity.status = updateMenuRequestDto.status();
        menuEntity.imageUrl = updateMenuRequestDto.imageUrl();
        menuEntity.description = updateMenuRequestDto.description();
        menuEntity.price = updateMenuRequestDto.price();

        return menuEntity;
    }
}
