package com.twopro.deliveryapp.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreDeliveryArea is a Querydsl query type for StoreDeliveryArea
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreDeliveryArea extends EntityPathBase<StoreDeliveryArea> {

    private static final long serialVersionUID = -1469160059L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreDeliveryArea storeDeliveryArea = new QStoreDeliveryArea("storeDeliveryArea");

    public final QDeliveryArea deliveryArea;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final QStore store;

    public QStoreDeliveryArea(String variable) {
        this(StoreDeliveryArea.class, forVariable(variable), INITS);
    }

    public QStoreDeliveryArea(Path<? extends StoreDeliveryArea> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreDeliveryArea(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreDeliveryArea(PathMetadata metadata, PathInits inits) {
        this(StoreDeliveryArea.class, metadata, inits);
    }

    public QStoreDeliveryArea(Class<? extends StoreDeliveryArea> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryArea = inits.isInitialized("deliveryArea") ? new QDeliveryArea(forProperty("deliveryArea")) : null;
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

