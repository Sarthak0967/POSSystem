package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.models.Refund;

public class RefundMapper {

    public static RefundDto toDto(Refund refund) {
        return RefundDto.builder()
                .id(refund.getId())
                .orderId(refund.getOrder().getId())
                .reason(refund.getReason())
                .cashierName(refund.getCashier().getUsername())
                .branchId(refund.getBranch().getId())
                .amount(refund.getAmount())
                .shiftReportId(refund.getShiftReport()!=null ? refund.getShiftReport().getId() : null)
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
