package com.twopro.deliveryapp.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = -1502818108L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final com.twopro.deliveryapp.common.entity.QBaseEntity _super = new com.twopro.deliveryapp.common.entity.QBaseEntity(this);

    public final com.twopro.deliveryapp.common.entity.QAddress address;

    public final QCategory category;

    public final StringPath closedDays = createString("closedDays");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deletedBy = _super.deletedBy;

    public final NumberPath<Integer> deliveryTip = createNumber("deliveryTip", Integer.class);

    public final EnumPath<com.twopro.deliveryapp.common.enumType.OrderType> deliveryType = createEnum("deliveryType", com.twopro.deliveryapp.common.enumType.OrderType.class);

    public final NumberPath<Integer> minimumOrderPrice = createNumber("minimumOrderPrice", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath operatingHours = createString("operatingHours");

    public final StringPath phone = createString("phone");

    public final StringPath pictureUrl = createString("pictureUrl");

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final NumberPath<Integer> reviewCount = createNumber("reviewCount", Integer.class);

    public final EnumPath<com.twopro.deliveryapp.common.enumType.StoreStatus> status = createEnum("status", com.twopro.deliveryapp.common.enumType.StoreStatus.class);

    public final ListPath<StoreDeliveryArea, QStoreDeliveryArea> storeDeliveryAreas = this.<StoreDeliveryArea, QStoreDeliveryArea>createList("storeDeliveryAreas", StoreDeliveryArea.class, QStoreDeliveryArea.class, PathInits.DIRECT2);

    public final ComparablePath<java.util.UUID> storeId = createComparable("storeId", java.util.UUID.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.twopro.deliveryapp.common.entity.QAddress(forProperty("address")) : null;
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

