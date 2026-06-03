package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.domain.PaymentType;
import com.sarthak.POSsystem.models.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data@Builder
public class OrderDto {

    private Long id;

    private Double totalAmount;

    private Long branchId;
    private Long customerId;

    private LocalDateTime createdAt;

    private BranchDto branch;
    private PaymentType paymentType;

    private UserDto cashier;


    private Customer customer;

    private List<OrderItemDto> items;
}
