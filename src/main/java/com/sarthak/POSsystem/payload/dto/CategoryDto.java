package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.models.Store;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CategoryDto {
    private  Long id;
    private String name;
    private Long storeId;

}
