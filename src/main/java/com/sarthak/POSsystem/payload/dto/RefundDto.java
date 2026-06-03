package com.sarthak.POSsystem.payload.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarthak.POSsystem.domain.PaymentType;
import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.models.Orders;
import com.sarthak.POSsystem.models.ShiftReport;
import com.sarthak.POSsystem.models.Users;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.time.LocalDateTime;

@Builder
@Data
public class RefundDto {
    private Long id;


    private OrderDto order;
    private Long orderId;

    private String reason;

    private Double amount;

//    private ShiftReport shiftReport;
    private Long shiftReportId;


    private UserDto cashier;
    private String cashierName;


    private BranchDto branch;
    private Long branchId;

    private LocalDateTime createdAt;


    private PaymentType paymentType;
}
