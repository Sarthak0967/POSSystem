package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDto {


    private Long id;


    private BranchDto branch;


    private ProductDto product;

    private Long branchId;
    private Long productId;


    private Integer quantity;

    private LocalDateTime lastUpdated;
}
