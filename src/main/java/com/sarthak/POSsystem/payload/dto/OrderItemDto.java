package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.models.Orders;
import com.sarthak.POSsystem.models.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data@Builder
public class OrderItemDto {

    private Long id;

    private Integer quantity;

    private Double price;

    private ProductDto product;

    private Long productId;

    private Long orderId;
}
