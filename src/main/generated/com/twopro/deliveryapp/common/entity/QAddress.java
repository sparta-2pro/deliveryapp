package com.twopro.deliveryapp.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAddress extends BeanPath<Address> {

    private static final long serialVersionUID = -55905877L;

    public static final QAddress address = new QAddress("address");

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath eupMyeonDong = createString("eupMyeonDong");

    public final StringPath jibunAddress = createString("jibunAddress");

    public final StringPath roadAddress = createString("roadAddress");

    public final StringPath sido = createString("sido");

    public final StringPath sigungu = createString("sigungu");

    public QAddress(String variable) {
        super(Address.class, forVariable(variable));
    }

    public QAddress(Path<? extends Address> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddress(PathMetadata metadata) {
        super(Address.class, metadata);
    }

}

