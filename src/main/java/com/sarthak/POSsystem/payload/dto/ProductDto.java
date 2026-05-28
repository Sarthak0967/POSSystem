package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.models.Category;
import com.sarthak.POSsystem.models.Store;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDto {
    private Long id;


    private String name;

    private String sku;

    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String brand;


    private String imageUrl;
    private CategoryDto category;
    private Long categoryId;


    private Long storeId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
