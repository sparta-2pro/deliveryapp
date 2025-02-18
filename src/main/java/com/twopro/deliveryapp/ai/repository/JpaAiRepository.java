package com.twopro.deliveryapp.ai.repository;

import com.twopro.deliveryapp.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaAiRepository extends JpaRepository<Ai, UUID> {
}
