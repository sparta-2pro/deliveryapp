package com.twopro.deliveryapp.ai.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twopro.deliveryapp.ai.dto.AiResponseDto;
import com.twopro.deliveryapp.ai.entity.Ai;
import com.twopro.deliveryapp.ai.entity.QAi;
import com.twopro.deliveryapp.menu.entity.QMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AiRepositoryImpl implements AiRepository {

    private final JpaAiRepository jpaAiRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    public Ai saveAiService(Ai ai) {
        return jpaAiRepository.save(ai);
    }

    @Override
    public Optional<Ai> findAiServiceById(UUID aiId) {
        return jpaAiRepository.findById(aiId);
    }

    @Override
    public List<Ai> findAllAiServicesByStoreId(UUID storeId, Long size) {
        return jpaAiRepository.findAllAiServiceByStoreId(storeId, size);
    }

    @Override
    public List<Ai> findAllAiServicesByStoreId(UUID storeId, Long size, UUID lastAiId) {
        return jpaAiRepository.findAllAiServiceByStoreId(storeId, size, lastAiId);
    }

    @Override
    public List<AiResponseDto> findAllAiServiceByFilter(
            LocalDate startDate, LocalDate endDate, String menuName, UUID storeId, int size, UUID lastAiId
    ) {
        QAi ai = QAi.ai;
        QMenu menu = QMenu.menu;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null) {
            builder.and(ai.createdAt.goe(startDate.atStartOfDay()));
        }

        if (endDate != null) {
            builder.and(ai.createdAt.loe(endDate.atTime(23, 59, 59)));
        }

        if (menuName != null) {
            // TOdO 성능 생각해보고 contain 으로 해도 될듯
            builder.and(ai.menu.name.eq(menuName));
        }

        if (lastAiId != null) {
            builder.and((ai.aiId.loe(lastAiId)));
        }

        builder.and(ai.menu.store.storeId.eq(storeId));

        return queryFactory
                .select(
                        Projections.constructor(
                                AiResponseDto.class,
                                ai.question,
                                ai.aiAnswer,
                                ai.createdAt
                        )
                )
                .from(ai)
                .leftJoin(ai.menu, menu)
                .where(builder)
                .orderBy(ai.createdAt.desc())
                .limit(size)
                .fetch();
    }
}
