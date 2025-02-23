package com.twopro.deliveryapp.common.scheduler;

import com.twopro.deliveryapp.review.dto.ReviewFindStoreRatingResponse;
import com.twopro.deliveryapp.review.repository.ReviewRepository;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S") // Scheduler Lock 사용 가능 설정 (기본 30초동안 Lock)
public class Scheduler {
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Scheduled(cron = "0 0 2 * * *")
    @SchedulerLock(name = "StoreReviewUpdateLock")
    public void ChangeStoreRating() {
        try {
            List<Store> storeList = storeRepository.findAll();
            for (Store store : storeList) {
                ReviewFindStoreRatingResponse averageRatingByStoreId = reviewRepository.findAverageRatingByStoreId(store.getStoreId());
                store.updateRating(averageRatingByStoreId.getRating(), averageRatingByStoreId.getRatingCount().intValue());
            }
        } catch (Exception e) {
            log.error("리뷰 스케쥴러 ERROR :: {}", e);
            throw new RuntimeException("가게 리뷰 평점 스케쥴러에서 문제가 일어났습니다.");
        }

    }
}