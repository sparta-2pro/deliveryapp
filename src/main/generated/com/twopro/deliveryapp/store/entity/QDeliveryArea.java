package com.twopro.deliveryapp.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryArea is a Querydsl query type for DeliveryArea
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryArea extends EntityPathBase<DeliveryArea> {

    private static final long serialVersionUID = 1391876446L;

    public static final QDeliveryArea deliveryArea = new QDeliveryArea("deliveryArea");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final ListPath<StoreDeliveryArea, QStoreDeliveryArea> storeDeliveryAreas = this.<StoreDeliveryArea, QStoreDeliveryArea>createList("storeDeliveryAreas", StoreDeliveryArea.class, QStoreDeliveryArea.class, PathInits.DIRECT2);

    public QDeliveryArea(String variable) {
        super(DeliveryArea.class, forVariable(variable));
    }

    public QDeliveryArea(Path<? extends DeliveryArea> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryArea(PathMetadata metadata) {
        super(DeliveryArea.class, metadata);
    }

}

