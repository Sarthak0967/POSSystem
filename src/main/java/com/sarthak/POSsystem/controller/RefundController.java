package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.models.Refund;
import com.sarthak.POSsystem.payload.dto.RefundDto;
import com.sarthak.POSsystem.payload.dto.RefundMapper;
import com.sarthak.POSsystem.payload.response.ApiResponse;
import com.sarthak.POSsystem.service.RefundService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.sql.Ref;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {
    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundDto> createRefund(
            @RequestBody RefundDto refundDto
            ) throws Exception {
        RefundDto refund = refundService.createRefund(refundDto);

        return ResponseEntity.ok(refund);
    }

    @GetMapping
    public ResponseEntity<List<RefundDto>> getAllRefunds() throws Exception {
        List<RefundDto> refunds = refundService.getAllRefunds();
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/cashier/{id}")
    public ResponseEntity<List<RefundDto>> getRefundByCashier(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(refundService.getRefundByCashier(id));
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<List<RefundDto>> getRefundByBranch(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(refundService.getRefundByBranch(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRefund(
            @PathVariable Long id
    ) throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        refundService.deleteRefund(id);
        apiResponse.setMessage("Deleted Successfully");

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundDto> getRefundById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(refundService.getRefundById(id));
    }

    @GetMapping("/shift/{id}")
    public ResponseEntity<List<RefundDto>> getRefundByShiftReport(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(refundService.getRefundByShiftReport(id));
    }

    @GetMapping("/cashier/{id}/range")
    public ResponseEntity<List<RefundDto>> getRefundByCashierIdAndDateRange(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
            ) throws Exception {
        return ResponseEntity.ok(refundService.getRefundByCashierIdAndDateRange(id, from, to));
    }

}
