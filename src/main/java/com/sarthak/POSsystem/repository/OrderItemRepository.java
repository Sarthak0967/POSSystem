package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
