package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.domain.OrderStatus;
import com.sarthak.POSsystem.domain.PaymentType;
import com.sarthak.POSsystem.payload.dto.OrderDto;
import com.sarthak.POSsystem.payload.dto.OrderItemDto;

import java.util.List;

public interface OrderService {
    OrderDto createdOrder(OrderDto orderDto) throws Exception;
    OrderDto getOrderById(Long orderId) throws  Exception;
    List<OrderDto> getOrderByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status) throws Exception;
    List<OrderDto> getOrderByCashier(Long cashierId) throws  Exception;
    void deleteOrder(Long id) throws Exception;
    List<OrderDto> getTodayOrdersByBranch(Long branchId) throws Exception;
    List<OrderDto> getOrdersByCustomerId(Long customerId) throws Exception;
    List<OrderDto> getTop5RecentOrderByBranchId(Long branchId) throws Exception;

}
