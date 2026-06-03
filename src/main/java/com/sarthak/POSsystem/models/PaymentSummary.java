package com.sarthak.POSsystem.models;

import com.sarthak.POSsystem.domain.PaymentType;
import lombok.Builder;
import lombok.Data;

@Data
public class PaymentSummary {
    private PaymentType paymentType;
    private Double totalAmount;
    private int transactionCount;
    private double percentage;

}
