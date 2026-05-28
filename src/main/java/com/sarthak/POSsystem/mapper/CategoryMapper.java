package com.sarthak.POSsystem.mapper;

import com.sarthak.POSsystem.models.Category;
import com.sarthak.POSsystem.payload.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore()!=null ? category.getStore().getId() : null)
                .build();
    }


}
