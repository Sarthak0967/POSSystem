package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Inventory;
import com.sarthak.POSsystem.payload.dto.InventoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductIdAndBranchId(Long productId, Long branchId);
    List<Inventory> findByBranchId(Long branchId);
}
