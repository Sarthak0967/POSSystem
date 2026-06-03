package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.models.ShiftReport;
import com.sarthak.POSsystem.payload.dto.ShiftReportDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {

    ShiftReportDto startShift() throws Exception;

    ShiftReportDto shiftEnd(Long shiftReportId,
                         LocalDateTime shiftEnd) throws Exception;

    ShiftReportDto
    getShiftReportById(Long id) throws Exception;

    List<ShiftReportDto> getALlShiftReports();

    List<ShiftReportDto> getShiftReportsByCashier(Long cashierId) throws Exception;

    List<ShiftReportDto> getShiftReportByBranch(Long branchId) throws Exception;

    ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception;

    ShiftReportDto getShiftReportByCashierDate(Long cashierId, LocalDateTime date) throws Exception;

}
