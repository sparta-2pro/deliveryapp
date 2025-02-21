package com.twopro.deliveryapp.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 695364068L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.twopro.deliveryapp.common.entity.QBaseEntity _super = new com.twopro.deliveryapp.common.entity.QBaseEntity(this);

    public final com.twopro.deliveryapp.common.entity.QAddress address;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created_at = _super.created_at;

    //inherited
    public final StringPath created_by = _super.created_by;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted_at = _super.deleted_at;

    //inherited
    public final StringPath deleted_by = _super.deleted_by;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath message = createString("message");

    public final ListPath<com.twopro.deliveryapp.orderItem.Entity.OrderItem, com.twopro.deliveryapp.orderItem.Entity.QOrderItem> orderItems = this.<com.twopro.deliveryapp.orderItem.Entity.OrderItem, com.twopro.deliveryapp.orderItem.Entity.QOrderItem>createList("orderItems", com.twopro.deliveryapp.orderItem.Entity.OrderItem.class, com.twopro.deliveryapp.orderItem.Entity.QOrderItem.class, PathInits.DIRECT2);

    public final EnumPath<com.twopro.deliveryapp.common.enumType.OrderStatus> orderStatus = createEnum("orderStatus", com.twopro.deliveryapp.common.enumType.OrderStatus.class);

    public final EnumPath<com.twopro.deliveryapp.common.enumType.OrderType> orderType = createEnum("orderType", com.twopro.deliveryapp.common.enumType.OrderType.class);

    public final com.twopro.deliveryapp.payment.entity.QPayment payment;

    public final com.twopro.deliveryapp.store.entity.QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated_at = _super.updated_at;

    //inherited
    public final StringPath updated_by = _super.updated_by;

    public final com.twopro.deliveryapp.user.entity.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.twopro.deliveryapp.common.entity.QAddress(forProperty("address")) : null;
        this.payment = inits.isInitialized("payment") ? new com.twopro.deliveryapp.payment.entity.QPayment(forProperty("payment")) : null;
        this.store = inits.isInitialized("store") ? new com.twopro.deliveryapp.store.entity.QStore(forProperty("store"), inits.get("store")) : null;
        this.user = inits.isInitialized("user") ? new com.twopro.deliveryapp.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

