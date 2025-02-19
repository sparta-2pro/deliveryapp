package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DeliveryAreaRepository extends JpaRepository<DeliveryArea, UUID> {
}