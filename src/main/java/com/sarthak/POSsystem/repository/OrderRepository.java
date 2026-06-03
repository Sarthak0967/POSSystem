package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Orders;
import com.sarthak.POSsystem.models.Orders;
import com.sarthak.POSsystem.models.Users;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomerId(Long customerId);
    List<Orders> findByBranchId(Long branchId);
    List<Orders> findByCashierId(Long cashierId);
    List<Orders> findByBranchIdAndCreatedAtBetween(Long branchId, LocalDateTime from, LocalDateTime to);
    List<Orders> findByCashierAndCreatedAtBetween(Users cashier, LocalDateTime from, LocalDateTime to);
    List<Orders> findTop5ByBranchIdOrderByCreatedAtDesc(Long branchId);
}
