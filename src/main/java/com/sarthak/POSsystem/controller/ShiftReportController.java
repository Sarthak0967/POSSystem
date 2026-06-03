package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.models.ShiftReport;
import com.sarthak.POSsystem.payload.dto.ShiftReportDto;
import com.sarthak.POSsystem.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shiftReports")
public class ShiftReportController {
    private final ShiftReportService shiftReportService;

    @PostMapping("/start")
    public ResponseEntity<ShiftReportDto> startShift() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.startShift()
        );
    }

    @PatchMapping("/end")
    public ResponseEntity<ShiftReportDto> endShift() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.shiftEnd(null, null)
        );
    }

    @GetMapping("/current")
    public ResponseEntity<ShiftReportDto> getCurrentShiftProgress() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.getCurrentShiftProgress(null)
        );
    }

    @GetMapping("/cashier/{cashierId}/by-date")
    public ResponseEntity<ShiftReportDto> getShiftReportByDate(
            @PathVariable Long cashierId,
            @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDateTime date
    ) throws Exception {
        return ResponseEntity.ok(
                shiftReportService.getShiftReportByCashierDate(cashierId, date)
        );
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDto>> getShiftReportByCashier(
            @PathVariable Long cashierId
    ) throws Exception {
        return ResponseEntity.ok(
                shiftReportService.getShiftReportsByCashier(cashierId)
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDto>> getShiftReportByBranch(
            @PathVariable Long branchId
    ) throws Exception {
        return ResponseEntity.ok(
                shiftReportService.getShiftReportByBranch(branchId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftReportDto> getShiftReportById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(
                shiftReportService.getShiftReportById(id)
        );
    }




}
