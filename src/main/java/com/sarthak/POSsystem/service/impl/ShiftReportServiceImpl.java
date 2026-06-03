package com.sarthak.POSsystem.service.impl;

import com.razorpay.Order;
import com.sarthak.POSsystem.domain.PaymentType;
import com.sarthak.POSsystem.mapper.ShiftReportMapper;
import com.sarthak.POSsystem.models.*;
import com.sarthak.POSsystem.payload.dto.BranchDto;
import com.sarthak.POSsystem.payload.dto.ShiftReportDto;
import com.sarthak.POSsystem.repository.OrderRepository;
import com.sarthak.POSsystem.repository.RefundRepository;
import com.sarthak.POSsystem.repository.ShiftReportRepository;
import com.sarthak.POSsystem.service.ShiftReportService;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {


    private final ShiftReportRepository shiftReportRepository;
    private final UserService userService;
    private ShiftReportService shiftReportService;
    private RefundRepository refundRepository;
    private OrderRepository orderRepository;


    @Override
    public ShiftReportDto startShift() throws Exception {
        Users currentUser  = userService.getCurrentUser();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime startOfDay = start.withHour(0).withMinute(0).withSecond(0);

        LocalDateTime endOfDay = start.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> exitingShift = shiftReportRepository.findByCashierAndShiftStartBetween(currentUser, startOfDay, endOfDay);

        if(exitingShift.isPresent()) {
            throw new Exception("Shift already started today");
        }

        Branch branch = currentUser.getBranch();

        ShiftReport shiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .shiftStart(start)
                .branch(branch)
                .build();

        ShiftReport saved = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(saved);
    }

    @Override
    public ShiftReportDto shiftEnd(Long shiftReportId, LocalDateTime shiftEnd) throws Exception {
        Users currentUser = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
                .orElseThrow(
                        () -> new Exception("Shift not found")
                );

        shiftReport.setShiftEnd(shiftEnd);

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(),
                shiftReport.getShiftStart(), shiftReport.getShiftEnd()
        );

        double totalRefunds = refunds.stream().mapToDouble( refund -> refund.getAmount()!=null ? refund.getAmount(): 0.0).sum();

        List<Orders> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser,
                shiftReport.getShiftStart(),
                shiftReport.getShiftEnd()
        );

        int totalOrders = orders.size();

        double totalSales = orders.stream().mapToDouble(Orders::getTotalAmount).sum();

        double netSales = totalSales - totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSales(netSales);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport  = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(savedReport);
    }


    @Override
    public ShiftReportDto getShiftReportById(Long id) throws Exception {
        return ShiftReportMapper.toDto(shiftReportRepository.findById(id).orElseThrow(
                () -> new Exception("Shiftreport not found with the given id")
        ));
    }

    @Override
    public List<ShiftReportDto> getALlShiftReports() {
        List<ShiftReport> reports = shiftReportRepository.findAll();
        return reports.stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportsByCashier(Long cashierId) throws Exception {
        return shiftReportRepository.findByCashierId(cashierId).stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportByBranch(Long branchId) throws Exception {
        return shiftReportRepository.findByBranchId(branchId)
                .stream()
                .map(ShiftReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception {
        Users currentUser = userService.getCurrentUser();
        ShiftReport shiftReport = shiftReportRepository
                .findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
                .orElseThrow(
                        () -> new Exception("No active shift found for cashier")
                );

        LocalDateTime now = LocalDateTime.now();
        List<Orders> orders = orderRepository.findByCashierAndCreatedAtBetween(currentUser, shiftReport.getShiftStart(), now);

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(),
                shiftReport.getShiftStart(), shiftReport.getShiftEnd()
        );

        double totalRefunds = refunds.stream().mapToDouble( refund -> refund.getAmount()!=null ? refund.getAmount(): 0.0).sum();

        int totalOrders = orders.size();

        double totalSales = orders.stream().mapToDouble(Orders::getTotalAmount).sum();

        double netSales = totalSales - totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSales(netSales);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(savedReport);
    }

    @Override
    public ShiftReportDto getShiftReportByCashierDate(Long cashierId, LocalDateTime date) throws Exception {
        Users cashier = userService.getUserById(cashierId);

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(
                cashier, start, end
        ).orElseThrow(
                () -> new Exception("Shift report not found")
        );

        return ShiftReportMapper.toDto(report);
    }


    // Helper Methods


    private List<Orders> getRecentOrders(List<Orders> orders) {
       return orders.stream()
                .sorted(Comparator.comparing(Orders::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<Product> getTopSellingProducts(List<Orders> orders) {
        Map<Product, Integer> productSalesMap = new HashMap<>();

        for( Orders order: orders) {
            for (OrderItem item: order.getItems()) {
                Product product = item.getProduct();
                productSalesMap.put(product, productSalesMap.getOrDefault(product, 0)+item.getQuantity());
            }
        }

        return productSalesMap.entrySet().stream()
                .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<PaymentSummary> getPaymentSummaries(List<Orders> orders, double totalSales) {
        Map<PaymentType, List<Orders>> groupedByType = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getPaymentType() != null ? order.getPaymentType() : PaymentType.CASH));

        List<PaymentSummary> summaries = new ArrayList<>();
        for(Map.Entry<PaymentType, List<Orders>> entry:
                groupedByType.entrySet()) {
            double amount = entry.getValue().stream()
                    .mapToDouble(Orders::getTotalAmount).sum();

            int transactions = entry.getValue().size();

            double percentages = (amount / totalSales)*100;

//            PaymentSummary ps = PaymentSummary.builder()
//                    .percentage(percentages)
//                    .totalAmount(amount)
//                    .paymentType(entry.getKey())
//                    .build();

            PaymentSummary paymentSummary = new PaymentSummary();
            paymentSummary.setPaymentType(entry.getKey());
            paymentSummary.setTotalAmount(amount);
            paymentSummary.setTransactionCount(transactions);
            paymentSummary.setPercentage(percentages);

            summaries.add(paymentSummary);

        }


        return summaries;
    }
}
