package com.twopro.deliveryapp.review.repository;

import com.twopro.deliveryapp.review.dto.ReviewFindStoreRatingResponse;
import com.twopro.deliveryapp.review.entity.Review;
import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("select r from Review r where r.order.id =:orderId")
    Optional<Review> findFetchByOrderIdAndDeletedAtIsNull(UUID orderId);

    // 조인패치로 리뷰->회원 가져오기, 조인패치로 리뷰->오더->오더메뉴->메뉴로 한번에 가져오기
    @Query("select r from Review r join fetch r.user u join fetch r.order o join fetch o.store where r.id =:reviewId")
    Optional<Review> findFetchById(UUID reviewId);

    @Query("select r from Review r where r.id =:reviewId and r.deletedBy is null")
    Optional<Review> findByIdAndDeletedAtIsNull(UUID reviewId);

    @Query(value = "select r from Review r join fetch r.user u join fetch r.order o join fetch o.store where o.user.userId =:userId",
    countQuery = "select count(r) from Review r where r.user.userId = :userId")
    Page<Review> findFetchByUserId(Long userId, Pageable pageable);

    @Query("select new com.twopro.deliveryapp.review.dto.ReviewFindStoreRatingResponse(max(s.storeId), avg(r.rating), count(r.id)) " +
            "from Review r join r.order o join o.store s " +
            "where s.storeId = :storeId and r.deletedBy is null")
    ReviewFindStoreRatingResponse findAverageRatingByStoreId(UUID storeId);

}