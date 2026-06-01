package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.payload.dto.InventoryDto;
import com.sarthak.POSsystem.payload.response.ApiResponse;
import com.sarthak.POSsystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(
            @RequestBody InventoryDto inventoryDto
    ) throws Exception {
        return ResponseEntity.ok(
                inventoryService.createInventory(inventoryDto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateInventory(
            @RequestBody InventoryDto inventoryDto,
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(
            @PathVariable Long id
    ) throws Exception {
        inventoryService.deleteInventory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Inventory Deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDto>> getInventoryByBranch(
            @PathVariable Long branchId
    ) {
        return  ResponseEntity.ok(inventoryService.getAllInventoryByBranchId(branchId));
    }

    @GetMapping("/branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDto> getInventoryByBranchAndProductId(
            @PathVariable Long branchId,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductIdAndBranchId(productId, branchId));
    }
}
