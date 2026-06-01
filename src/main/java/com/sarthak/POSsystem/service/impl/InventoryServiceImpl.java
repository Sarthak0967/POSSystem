package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.mapper.InventoryMapper;
import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.models.Inventory;
import com.sarthak.POSsystem.models.Product;
import com.sarthak.POSsystem.payload.dto.InventoryDto;
import com.sarthak.POSsystem.repository.BranchRepository;
import com.sarthak.POSsystem.repository.InventoryRepository;
import com.sarthak.POSsystem.repository.ProductRepository;
import com.sarthak.POSsystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {


        Branch branch = branchRepository.findById(inventoryDto.getBranchId()).orElseThrow(
                () -> new Exception("Branch not exist")
        );

        Product product = productRepository.findById(inventoryDto.getBranchId())
                .orElseThrow(
                        () -> new Exception("Product not exist")
                );

        Inventory inventory = InventoryMapper.toEntity(inventoryDto, branch, product);
        Inventory saved = inventoryRepository.save(inventory);
        return InventoryMapper.toDto(saved);
    }

    @Override
    public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Inventory does not exist...")
                );

        inventory.setQuantity(inventoryDto.getQuantity());
        Inventory updated = inventoryRepository.save(inventory);

        return InventoryMapper.toDto(updated);


    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Inventory does not exist")
                );

        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);

        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDto getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Inventory does not exist")
                );
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);

        return inventories.stream()
                .map(
                        InventoryMapper::toDto
                ).collect(Collectors.toList());
    }
}
