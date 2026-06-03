package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.models.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data@Builder
public class ShiftReportDto {

    private Long id;
    private Long cashierId;
    private Long branchId;

    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;

    private Double totalSales;

    private Double totalRefunds;

    private Double netSales;

    private int totalOrders;

    private UserDto cashier;

    private BranchDto branch;

    private List<PaymentSummary> paymentSummaries;

    private List<ProductDto> topSellingProducts;

    private List<OrderDto> recentOrders;

    private List<RefundDto> refunds;
}
