package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.ShiftReport;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.ShiftReportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShiftReportRepository extends JpaRepository<ShiftReport, Long> {
    List<ShiftReport> findByCashierId(Long cashierId);
    List<ShiftReport> findByBranchId(Long branchId);

    Optional<ShiftReport> findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(Users cashier);

    Optional<ShiftReport> findByCashierAndShiftStartBetween(Users cashier, LocalDateTime start, LocalDateTime end);


}
