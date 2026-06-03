package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Refund;
import com.sarthak.POSsystem.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByCashierIdAndCreatedAtBetween(
            Long cashierId, LocalDateTime from, LocalDateTime to
    );

    List<Refund> findByCashierId(Long cashierId);
    List<Refund> findByShiftReportId(Long id);
    List<Refund> findByBranchId(Long id);
}
