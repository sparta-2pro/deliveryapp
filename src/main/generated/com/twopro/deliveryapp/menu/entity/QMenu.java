package com.twopro.deliveryapp.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = -815278916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenu menu = new QMenu("menu");

    public final com.twopro.deliveryapp.common.entity.QBaseEntity _super = new com.twopro.deliveryapp.common.entity.QBaseEntity(this);

    public final ListPath<com.twopro.deliveryapp.ai.entity.Ai, com.twopro.deliveryapp.ai.entity.QAi> aiList = this.<com.twopro.deliveryapp.ai.entity.Ai, com.twopro.deliveryapp.ai.entity.QAi>createList("aiList", com.twopro.deliveryapp.ai.entity.Ai.class, com.twopro.deliveryapp.ai.entity.QAi.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created_at = _super.created_at;

    //inherited
    public final StringPath created_by = _super.created_by;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted_at = _super.deleted_at;

    //inherited
    public final StringPath deleted_by = _super.deleted_by;

    public final StringPath description = createString("description");

    public final StringPath imageUrl = createString("imageUrl");

    public final ComparablePath<java.util.UUID> menuId = createComparable("menuId", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<MenuStatus> status = createEnum("status", MenuStatus.class);

    public final com.twopro.deliveryapp.store.entity.QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated_at = _super.updated_at;

    //inherited
    public final StringPath updated_by = _super.updated_by;

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new com.twopro.deliveryapp.store.entity.QStore(forProperty("store"), inits.get("store")) : null;
    }

}

