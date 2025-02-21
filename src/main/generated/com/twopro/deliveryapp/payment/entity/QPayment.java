package com.twopro.deliveryapp.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 2145677604L;

    public static final QPayment payment = new QPayment("payment");

    public final com.twopro.deliveryapp.common.entity.QBaseEntity _super = new com.twopro.deliveryapp.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created_at = _super.created_at;

    //inherited
    public final StringPath created_by = _super.created_by;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted_at = _super.deleted_at;

    //inherited
    public final StringPath deleted_by = _super.deleted_by;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final EnumPath<com.twopro.deliveryapp.common.enumType.PaymentProvider> paymentProvider = createEnum("paymentProvider", com.twopro.deliveryapp.common.enumType.PaymentProvider.class);

    public final EnumPath<com.twopro.deliveryapp.common.enumType.PaymentStatus> paymentStatus = createEnum("paymentStatus", com.twopro.deliveryapp.common.enumType.PaymentStatus.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated_at = _super.updated_at;

    //inherited
    public final StringPath updated_by = _super.updated_by;

    public QPayment(String variable) {
        super(Payment.class, forVariable(variable));
    }

    public QPayment(Path<? extends Payment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPayment(PathMetadata metadata) {
        super(Payment.class, metadata);
    }

}

