package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.domain.OrderStatus;
import com.sarthak.POSsystem.domain.PaymentType;
import com.sarthak.POSsystem.payload.dto.OrderDto;
import com.sarthak.POSsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody OrderDto orderDto
    ) throws Exception {
        return ResponseEntity.ok(orderService.createdOrder(orderDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderDto>> getOrdersByBranch(
            @PathVariable Long branchId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long cashierId,
            @RequestParam(required = false)PaymentType paymentType,
            @RequestParam(required = false) OrderStatus status
            ) throws Exception {
        return ResponseEntity.ok(orderService.getOrderByBranch(branchId, customerId, cashierId, paymentType, status));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDto>> getOrderByCashier(
            @PathVariable Long cashierId
    ) throws Exception {
        return ResponseEntity.ok(orderService.getOrderByCashier(cashierId));
    }

    @GetMapping("/today/branch/{id}")
    public ResponseEntity<List<OrderDto>> getTodayOrders(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getTodayOrdersByBranch(id));
    }


    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderDto>> getCustomersOrders(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(id));
    }

    @GetMapping("/recent/{id}")
    public ResponseEntity<List<OrderDto>> getRecentOrders(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getTop5RecentOrderByBranchId(id));
    }


}
