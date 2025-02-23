package com.twopro.deliveryapp.review.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "p_review")
@Getter
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @Column(name = "review_id")
    @GeneratedValue
    private UUID id;

    private String contents;
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public static Review createReview(int rating, String content, Order order, User user) {
        Review review = new Review();
        review.rating = rating;
        review.contents = content;
        review.order = order;
        review.user = user;
        return review;
    }
}
