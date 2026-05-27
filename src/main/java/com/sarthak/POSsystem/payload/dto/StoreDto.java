package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.domain.StoreStatus;
import com.sarthak.POSsystem.models.StoreContact;
import com.sarthak.POSsystem.models.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class StoreDto {

    private Long id;

    private String brand;


    private UserDto storeAdmin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String description;
    private String storeType;
    private StoreStatus status;

    private StoreContact contact;




}
