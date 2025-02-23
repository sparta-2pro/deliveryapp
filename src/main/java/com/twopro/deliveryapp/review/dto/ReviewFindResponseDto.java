package com.twopro.deliveryapp.review.dto;

import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ReviewFindResponseDto {
    private UUID ReviewId; //리뷰ID
    private String nickname; //주문자 닉네임
    private Long userId; //유저ID
    private int rating; // 작성한 평점
    private String contents; // 작성한 리뷰내용
    private List<ReviewMenuDto> menus; // 주문한 메뉴들
    private LocalDateTime createdAt; // 작성한 시간
    private UUID storeId; // 주문한 가게ID
    private String storeName; //주문한 가게 이름
    private UUID orderId; // 주문ID

    public ReviewFindResponseDto(UUID ReviewId, String nickname, Long userId, int rating, String contents, List<ReviewMenuDto> reviewMenuDtoList, LocalDateTime createdAt, UUID storeId, String storeName, UUID orderId) {
        this.ReviewId = ReviewId;
        this.nickname = nickname;
        this.userId = userId;
        this.rating = rating;
        this.contents = contents;
        this.menus = reviewMenuDtoList;
        this.createdAt = createdAt;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orderId = orderId;
    }
}
