package com.twopro.deliveryapp.review.repository;

import com.twopro.deliveryapp.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
