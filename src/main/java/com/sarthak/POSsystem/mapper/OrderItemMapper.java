package com.sarthak.POSsystem.mapper;

import com.sarthak.POSsystem.models.OrderItem;
import com.sarthak.POSsystem.payload.dto.OrderItemDto;

public class OrderItemMapper {
    public static OrderItemDto toDto(OrderItem orderItem) {
        if(orderItem == null) return null;
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .product(ProductMapper.toDto(orderItem.getProduct()))
                .build();
    }
}
