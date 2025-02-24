package com.twopro.deliveryapp.menu.entity;

import com.twopro.deliveryapp.ai.entity.Ai;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.menu.dto.AddMenuRequestDto;
import com.twopro.deliveryapp.menu.dto.UpdateMenuRequestDto;
import com.twopro.deliveryapp.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "P_MENU")
@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID menuId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Store store;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ai> aiList;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private MenuStatus status;

    // TODO s3? test url?
    private String imageUrl;

    private String description;

    @Column(nullable = false)
    private int price;

    public static Menu of(AddMenuRequestDto addMenuRequestDto) {
        return Menu.builder()
                .name(addMenuRequestDto.name())
                .status(addMenuRequestDto.status())
                .imageUrl(addMenuRequestDto.imageUrl())
                .description(addMenuRequestDto.description())
                .price(addMenuRequestDto.price())
                .build();
    }

    public static Menu update(Menu menu, UpdateMenuRequestDto updateMenuRequestDto) {
        menu.name = updateMenuRequestDto.name();
        menu.status = updateMenuRequestDto.status();
        menu.imageUrl = updateMenuRequestDto.imageUrl();
        menu.description = updateMenuRequestDto.description();
        menu.price = updateMenuRequestDto.price();

        return menu;
    }
}
