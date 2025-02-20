package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}