package com.sarthak.POSsystem.mapper;

import com.sarthak.POSsystem.models.ShiftReport;
import com.sarthak.POSsystem.payload.dto.RefundMapper;
import com.sarthak.POSsystem.payload.dto.ShiftReportDto;
import com.sarthak.POSsystem.payload.dto.UserDto;

import java.util.stream.Collectors;

public class ShiftReportMapper {

    public static ShiftReportDto toDto(ShiftReport entity) {
        return ShiftReportDto.builder()
                .id(entity.getId())
                .shiftStart(entity.getShiftStart())
                .shiftEnd(entity.getShiftEnd())
                .totalOrders(entity.getTotalOrders())
                .totalSales(entity.getTotalSales())
                .totalRefunds(entity.getTotalRefunds())
                .netSales(entity.getNetSales())
                .totalOrders(entity.getTotalOrders())
                .cashier(UserMapper.toDTO(entity.getCashier()))
                .cashierId(entity.getCashier().getId())
                .branchId(entity.getBranch().getId())
                .recentOrders(entity.getRecentOrders()!=null ? entity.getRecentOrders().stream().map(OrderMapper::toDto).collect(Collectors.toList()): null)
                .topSellingProducts(entity.getTopSellingProducts() != null ? entity.getTopSellingProducts().stream().map(ProductMapper::toDto).collect(Collectors.toList()): null)
                .refunds(entity.getRefunds() != null ? entity.getRefunds().stream().map(RefundMapper::toDto).collect(Collectors.toList()): null)
                .paymentSummaries(entity.getPaymentSummaries())
                .build();
    }


}
