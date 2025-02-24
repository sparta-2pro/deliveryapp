package com.twopro.deliveryapp.store.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twopro.deliveryapp.store.entity.QStore;
import com.twopro.deliveryapp.store.entity.Store;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.twopro.deliveryapp.store.entity.QStore.store;

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

        OrderSpecifier<?>[] orderSpecifiers = getSortedColumn(pageable);

        List<Store> stores = queryFactory.selectFrom(store)
                .where(predicate)
                .orderBy(orderSpecifiers) // 사용
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(store)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(stores, pageable, total);
    }

    private OrderSpecifier<?>[] getSortedColumn(Pageable pageable) {
        return pageable.getSort().stream()
                .map(order -> {
                    switch (order.getProperty()) {
                        case "created_at":
                            return order.isAscending() ? store.createdAt.asc() : store.createdAt.desc();
                        case "name":
                            return order.isAscending() ? store.name.asc() : store.name.desc();
                        default:
                            throw new IllegalArgumentException("정렬이 지원되지 않는 속성입니다: " + order.getProperty());
                    }
                })
                .filter(Objects::nonNull)
                .toArray(OrderSpecifier[]::new);
    }
}