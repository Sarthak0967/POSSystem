package com.sarthak.POSsystem.mapper;

import ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble;
import com.sarthak.POSsystem.models.Category;
import com.sarthak.POSsystem.models.Product;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.payload.dto.ProductDto;

public class ProductMapper {

    public static ProductDto toDto(Product product){

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .category(CategoryMapper.toDto(product.getCategory()))
                .brand(product.getBrand())
                .storeId(product.getStore()!=null ? product.getId() : null)
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductDto productDto, Store store, Category category) {
        return Product.builder()
                .name(productDto.getName())
                .store(store)
                .category(category)
                .sku(productDto.getSku())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .sellingPrice(productDto.getSellingPrice())
                .brand(productDto.getBrand())
                .updatedAt(productDto.getUpdatedAt())
                .build();
    }


}
