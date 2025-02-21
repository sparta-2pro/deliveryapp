package com.twopro.deliveryapp.cart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartMenu is a Querydsl query type for CartMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartMenu extends EntityPathBase<CartMenu> {

    private static final long serialVersionUID = 484311357L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartMenu cartMenu = new QCartMenu("cartMenu");

    public final QCart cart;

    public final ComparablePath<java.util.UUID> cartMenuId = createComparable("cartMenuId", java.util.UUID.class);

    public final com.twopro.deliveryapp.menu.entity.QMenu menu;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QCartMenu(String variable) {
        this(CartMenu.class, forVariable(variable), INITS);
    }

    public QCartMenu(Path<? extends CartMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartMenu(PathMetadata metadata, PathInits inits) {
        this(CartMenu.class, metadata, inits);
    }

    public QCartMenu(Class<? extends CartMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart"), inits.get("cart")) : null;
        this.menu = inits.isInitialized("menu") ? new com.twopro.deliveryapp.menu.entity.QMenu(forProperty("menu"), inits.get("menu")) : null;
    }

}

