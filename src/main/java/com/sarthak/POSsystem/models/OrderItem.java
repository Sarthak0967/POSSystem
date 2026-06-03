package com.sarthak.POSsystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.security.ProtectionDomain;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer quantity;

    private Double price;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Orders order;

}
