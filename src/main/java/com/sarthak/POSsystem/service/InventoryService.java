package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.models.Inventory;
import com.sarthak.POSsystem.payload.dto.InventoryDto;

import java.util.List;

public interface InventoryService {

    InventoryDto createInventory(InventoryDto inventoryDto) throws Exception;
    InventoryDto updateInventory(Long id, InventoryDto inventoryDto) throws Exception;
    void deleteInventory(Long id) throws Exception;
    InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId);
    InventoryDto getInventoryById(Long id) throws Exception;
    List<InventoryDto> getAllInventoryByBranchId(Long branchId);
}
