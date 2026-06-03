package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.domain.OrderStatus;
import com.sarthak.POSsystem.domain.PaymentType;
import com.sarthak.POSsystem.mapper.OrderMapper;
import com.sarthak.POSsystem.models.*;
import com.sarthak.POSsystem.payload.dto.OrderDto;
import com.sarthak.POSsystem.payload.dto.OrderItemDto;
import com.sarthak.POSsystem.repository.OrderRepository;
import com.sarthak.POSsystem.repository.ProductRepository;
import com.sarthak.POSsystem.service.OrderService;
import com.sarthak.POSsystem.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public OrderDto createdOrder(OrderDto orderDto) throws Exception {
        Users cashier = userService.getCurrentUser();
        Branch branch = cashier.getBranch();
        if(branch == null ){
            throw new Exception("Cashier's branch not found");
        }
        Orders order = Orders.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDto.getCustomer())
                .paymentType(orderDto.getPaymentType())
                .build();

        List<OrderItem> orderItems = orderDto.getItems()
                .stream().map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(
                            () -> new EntityNotFoundException("Product not found")
                    );
                    return OrderItem.builder()
                            .product(product)
                            .quantity(itemDto.getQuantity())
                            .price(product.getSellingPrice()*itemDto.getQuantity())
                            .order(order)
                            .build();
                }).toList();
        double total = orderItems.stream().mapToDouble(
                OrderItem::getPrice
        ).sum();
        order.setTotalAmount(total);
        order.setItems(orderItems);
        Orders savedOrder = orderRepository.save(order);
        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId)
                .map(OrderMapper::toDto)
                .orElseThrow(
                () -> new Exception("Order not found with id")
        );
    }

    @Override
    public List<OrderDto> getOrderByBranch(Long branchId,
                                           Long customerId,
                                           Long cashierId,
                                           PaymentType paymentType,
                                           OrderStatus status) throws Exception {
        return orderRepository.findByBranchId(branchId)
                .stream()
                .filter(order ->  customerId == null ||
                        (order.getCustomer()!=null &&
                                order.getCustomer().getId().equals(customerId)))
                .filter(order -> cashierId == null ||
                        (order.getCashier()!=null
                                && order.getCashier().getId().equals(cashierId)))
                .filter(order -> paymentType == null ||
                        order.getPaymentType() == paymentType)
                .map(OrderMapper::toDto).collect(Collectors.toList())
                ;
    }

    @Override
    public List<OrderDto> getOrderByCashier(Long cashierId) throws Exception {
        return orderRepository.findByCashierId(cashierId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Orders order = orderRepository.findById(id).orElseThrow(
                () -> new Exception("Order not found")
        );
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getTodayOrdersByBranch(Long branchId) throws Exception {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        return orderRepository.findByBranchIdAndCreatedAtBetween(branchId, start, end)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(Long customerId) throws Exception {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getTop5RecentOrderByBranchId(Long branchId) throws Exception {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }
}
