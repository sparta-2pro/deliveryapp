package com.twopro.deliveryapp.review.service;

import com.twopro.deliveryapp.common.scheduler.Scheduler;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.service.OrderServiceImpl;
import com.twopro.deliveryapp.review.dto.*;
import com.twopro.deliveryapp.review.entity.Review;
import com.twopro.deliveryapp.review.exception.ReviewExistException;
import com.twopro.deliveryapp.review.exception.ReviewNotCustomerException;
import com.twopro.deliveryapp.review.exception.ReviewNotFoundException;
import com.twopro.deliveryapp.review.exception.ReviewNotWriteCustomerException;
import com.twopro.deliveryapp.review.repository.ReviewRepository;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.service.StoreService;
import com.twopro.deliveryapp.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderServiceImpl orderService;
    private final UserServiceImpl userService;
    private final StoreService storeService;
    private final Scheduler scheduler;

    // 리뷰 작성
    @Override
    @Transactional
    public ReviewCreateResponseDto createReview(Long userId, ReviewCreateRequestDto requestDto) {
        reviewRepository.findFetchByOrderIdAndDeletedAtIsNull(requestDto.getOrderId())
                .ifPresent(r -> {
                    throw new ReviewExistException("이미 등록한 리뷰가 있습니다", requestDto.getOrderId(), userId);
                });

        Order findOrder = orderService.findById(requestDto.getOrderId());
        if (!Objects.equals(userId, findOrder.getUser().getUserId())) {
            throw new ReviewNotCustomerException("주문한 유저가 아닙니다.", userId, findOrder.getUser().getUserId(), requestDto.getOrderId());
        }

        Review review = Review.createReview(requestDto.getRating(), requestDto.getContent(), findOrder, userService.findById(userId));
        reviewRepository.save(review);
        return new ReviewCreateResponseDto(review.getId());
    }

    //리뷰 단건 조회
    @Override
    public ReviewFindResponseDto findOne(Long userId, UUID reviewId) {
        Review findReview = reviewRepository.findFetchById(reviewId).orElseThrow(() -> new ReviewNotFoundException("리뷰가 존재하지 않습니다.", userId, reviewId));
        List<ReviewMenuDto> reviewMenuDtoList = findReview.getOrder().getOrderItems().
                stream().map(oi -> new ReviewMenuDto(oi.getMenu().getMenuId(), oi.getMenu().getName(), oi.getOrderPrice(), oi.getCount())).toList();
        return new ReviewFindResponseDto(findReview.getId(), findReview.getUser().getNickname(), findReview.getUser().getUserId(),
                findReview.getRating(), findReview.getContents(), reviewMenuDtoList, findReview.getCreatedAt(),
                findReview.getOrder().getStore().getStoreId(), findReview.getOrder().getStore().getName(), findReview.getOrder().getId());
    }

    //작성한 리뷰 전체 조회
    @Override
    public Page<ReviewFindResponseDto> findReviews(Long userId, Integer page, Integer size, String sortBy, Boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Review> findReviews = reviewRepository.findFetchByUserId(userId, pageable);

        return findReviews.map(findReview -> {
            List<ReviewMenuDto> reviewMenuDtoList = findReview.getOrder().getOrderItems().stream()
                    .map(oi -> new ReviewMenuDto(oi.getMenu().getMenuId(), oi.getMenu().getName(), oi.getOrderPrice(), oi.getCount()))
                    .toList();

            return new ReviewFindResponseDto(
                    findReview.getId(),
                    findReview.getUser().getNickname(),
                    findReview.getUser().getUserId(),
                    findReview.getRating(),
                    findReview.getContents(),
                    reviewMenuDtoList,
                    findReview.getCreatedAt(),
                    findReview.getOrder().getStore().getStoreId(),
                    findReview.getOrder().getStore().getName(),
                    findReview.getOrder().getId()
            );
        });
    }


    @Transactional
    @Override
    public void deleteReview(Long userId, UUID reviewId) {
        // 리뷰가 존재하는지, 해당 userId로 작성한 리뷰가 맞는지
        Review review = reviewRepository.findByIdAndDeletedAtIsNull(reviewId).orElseThrow(() -> new ReviewNotFoundException("리뷰가 존재하지 않습니다.", userId, reviewId));
        if (!review.getUser().getUserId().equals(userId)) {
            throw new ReviewNotWriteCustomerException("작성한 유저가 아닙니다.", userId, review.getUser().getUserId(), reviewId);
        }
        review.delete();
    }

    //가게 리뷰 평점 가져오기
    @Override
    @Transactional
    public ReviewFindStoreRatingResponse findStoreRating(UUID storeId) {
        Store store = storeService.findByID(storeId).orElseThrow();
        if(store.getReviewCount() < 1000){ // 리뷰 갯수가 1000개 이하면 바로 계산해서 보여주기
            ReviewFindStoreRatingResponse response = reviewRepository.findAverageRatingByStoreId(storeId);
            store.updateRating(response.getRating(), Math.toIntExact(response.getRatingCount()));
            return response;
        }
        //1000개 이상이라면 스케쥴러로 새벽 1시에 계산해서 store 가게에 넣어놓은 값 보여주기
        return new ReviewFindStoreRatingResponse(store.getStoreId(), store.getRating(), (long) store.getReviewCount());
    }

    public void forcingScheduler() {
        scheduler.ChangeStoreRating();
    }

    //다른 서비스에서 필요할 수 있어서 생성
    @Override
    public Review findById(UUID reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("리뷰가 존재하지 않습니다.", reviewId));
    }

    /**
     * Pageable 객체 생성 메서드
     */
    private Pageable createPageable(int page, Integer size, String sortBy, Boolean isAsc) {
        Sort.Direction direction = (isAsc != null && isAsc) ? Sort.Direction.ASC : Sort.Direction.DESC;

        String defaultSortBy = "createdAt";
        Sort sort = (sortBy == null || sortBy.trim().isEmpty())
                ? Sort.by(direction, defaultSortBy)
                : Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }
}
