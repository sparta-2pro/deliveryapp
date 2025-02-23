package com.twopro.deliveryapp.ai.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAi is a Querydsl query type for Ai
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAi extends EntityPathBase<Ai> {

    private static final long serialVersionUID = -731975026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAi ai = new QAi("ai");

    public final com.twopro.deliveryapp.common.entity.QBaseEntity _super = new com.twopro.deliveryapp.common.entity.QBaseEntity(this);

    public final StringPath aiAnswer = createString("aiAnswer");

    public final ComparablePath<java.util.UUID> aiId = createComparable("aiId", java.util.UUID.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deletedBy = _super.deletedBy;

    public final com.twopro.deliveryapp.menu.entity.QMenu menu;

    public final StringPath question = createString("question");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QAi(String variable) {
        this(Ai.class, forVariable(variable), INITS);
    }

    public QAi(Path<? extends Ai> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAi(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAi(PathMetadata metadata, PathInits inits) {
        this(Ai.class, metadata, inits);
    }

    public QAi(Class<? extends Ai> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new com.twopro.deliveryapp.menu.entity.QMenu(forProperty("menu"), inits.get("menu")) : null;
    }

}

