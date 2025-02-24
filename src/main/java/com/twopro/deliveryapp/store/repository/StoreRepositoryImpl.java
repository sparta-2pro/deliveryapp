package com.twopro.deliveryapp.store.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twopro.deliveryapp.store.entity.QStore;
import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Store> searchStores(UUID categoryId, String storeName, Pageable pageable) {
        QStore store = QStore.store;
        BooleanExpression predicate = store.isNotNull();

        if (categoryId != null) {
            predicate = predicate.and(store.category.id.eq(categoryId));
        }
        if (storeName != null && !storeName.isEmpty()) {
            predicate = predicate.and(store.name.containsIgnoreCase(storeName));
        }

        List<Store> stores = queryFactory.selectFrom(store)
                .where(predicate)
                .orderBy(store.created_at.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(store)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(stores, pageable, total);
    }
}